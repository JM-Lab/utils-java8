
package kr.jm.utils;

import kr.jm.utils.datastructure.JMMap;
import kr.jm.utils.enums.OS;
import kr.jm.utils.helper.JMOptional;
import kr.jm.utils.helper.JMPath;
import kr.jm.utils.helper.JMPathOperation;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * The type Jm icon factory.
 */
public class JMIconFactory {

	private OS os;
	private String unknownFileName;
	private Map<String, BufferedImage> bufferedImageCache;

    /**
	 * Instantiates a new Jm icon factory.
     */
    public JMIconFactory() {
		this.os = OS.getOS();
		this.unknownFileName = "?";
		this.bufferedImageCache = new ConcurrentHashMap<>();
		BufferedImage unknownBufferedImage =
				buildBufferedImageOfIconInOS(JMPath.getPath(unknownFileName));
		this.bufferedImageCache.put(unknownFileName, unknownBufferedImage);
		if (os.equals(OS.MAC))
			this.bufferedImageCache.put("/dev",
					buildBufferedImageOfIconInOS(JMPath.getCurrentPath()));
	}

	private Function<String, BufferedImage>
			getCachedBufferedImageFunction(Path path) {
		Supplier<BufferedImage> newValueSupplier =
				() -> buildBufferedImageOfIconInOS(path);
		return key -> JMMap.getOrPutGetNew(bufferedImageCache, key,
				newValueSupplier);
	}

	private String buildFileExtensionKey(String extension) {
		return unknownFileName + extension;
	}

    /**
	 * Build buffered image of icon in os buffered image.
     *
     * @param path the path
     * @return the buffered image
     */
    public BufferedImage buildBufferedImageOfIconInOS(Path path) {
		path = JMOptional
				.getNullableAndFilteredOptional(path, JMPath.NotExistFilter)
				.flatMap(JMPathOperation::createTempFilePathAsOpt).orElse(path);
		Icon iconInOS = os.getIcon(path.toFile());
		BufferedImage bufferedImage = new BufferedImage(iconInOS.getIconWidth(),
				iconInOS.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
		iconInOS.paintIcon(null, bufferedImage.getGraphics(), 0, 0);
		return bufferedImage;
	}

    /**
	 * Gets cached buffered image of icon in os.
     *
     * @param path the path
	 * @return the cached buffered image of icon in os
     */
    public BufferedImage getCachedBufferedImageOfIconInOS(Path path) {
		return getSpecialPathAsOpt(path)
				.map(getCachedBufferedImageFunction(path))
				.orElseGet(() -> buildCachedBufferedImageOfFileIconInOS(path));
	}

	private Optional<String> getSpecialPathAsOpt(Path path) {
		return JMOptional
				.getNullableAndFilteredOptional(path,
						JMPath.DirectoryFilter.or(JMPath.SymbolicLinkFilter)
								.or(JMPath.HiddenFilter))
				.map(Path::toAbsolutePath).map(Path::toString);
	}

	private BufferedImage buildCachedBufferedImageOfFileIconInOS(Path path) {
		return getFilePathExtensionKeyAsOpt(path)
				.map(getCachedBufferedImageFunction(path))
				.orElseGet(() -> bufferedImageCache.get(unknownFileName));
	}

	private Optional<String> getFilePathExtensionKeyAsOpt(Path path) {
		return JMPath.getFilePathExtensionAsOpt(path)
				.map(this::buildFileExtensionKey);
	}

}
