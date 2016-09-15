
package kr.jm.utils;

import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.swing.Icon;

import kr.jm.utils.datastructure.JMMap;
import kr.jm.utils.enums.OS;
import kr.jm.utils.helper.JMOptional;
import kr.jm.utils.helper.JMPath;
import kr.jm.utils.helper.JMPathOperation;

/**
 * A factory for creating JMIcon objects.
 */
public class JMIconFactory {

	private OS os;
	private String unknownFileName;
	private Map<String, BufferedImage> bufferedImageCache;

	/**
	 * Instantiates a new JM icon factory.
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
	 * Builds the buffered image of icon in OS.
	 *
	 * @param path
	 *            the path
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
	 * Gets the cached buffered image of icon in OS.
	 *
	 * @param path
	 *            the path
	 * @return the cached buffered image of icon in OS
	 */
	public BufferedImage getCachedBufferedImageOfIconInOS(Path path) {
		return getSpecialPathAsOpt(path)
				.map(getCachedBufferedImageFunction(path))
				.orElseGet(() -> buildCachedBufferedImageOfFileIconInOS(path));
	}

	private Optional<String> getSpecialPathAsOpt(Path path) {
		return JMOptional
				.getNullableAndFilteredOptional(path, JMPath.DirectoryFilter
						.or(JMPath.SymbolicLinkFilter).or(JMPath.HiddenFilter))
				.map(Path::toString);
	}

	private BufferedImage buildCachedBufferedImageOfFileIconInOS(Path path) {
		return getFilePathExtensionKeyAsOpt(path)
				.map(getCachedBufferedImageFunction(path))
				.orElseGet(() -> bufferedImageCache.get(unknownFileName));
	}

	private Optional<String> getFilePathExtensionKeyAsOpt(Path path) {
		return JMPath.getPathExtensionAsOpt(path)
				.map(this::buildFileExtensionKey);
	}

}
