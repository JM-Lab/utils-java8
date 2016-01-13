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

public class JMIconFactory {

	private OS os;
	private String unknownFileName;
	private Map<String, BufferedImage> bufferedImageCache;

	public JMIconFactory() {
		this.os = OS.getOS();
		this.unknownFileName = "?";
		this.bufferedImageCache = new ConcurrentHashMap<>();
		BufferedImage unknownBufferedImage = buildBufferedImageOfIconInOS(
				JMPath.getPath(unknownFileName));
		this.bufferedImageCache.put(unknownFileName, unknownBufferedImage);
		if (os.equals(OS.MAC))
			this.bufferedImageCache.put("/dev",
					buildBufferedImageOfIconInOS(JMPath.getCurrentPath()));
	}

	private Function<String, BufferedImage> getCachedBufferedImageFunction(
			Path path) {
		Supplier<BufferedImage> newValueSupplier = () -> buildBufferedImageOfIconInOS(
				path);
		return key -> JMMap.getOrPutGetNew(bufferedImageCache, key,
				newValueSupplier);
	}

	private String buildFileExtentionKey(String extention) {
		return unknownFileName + extention;
	}

	public BufferedImage buildBufferedImageOfIconInOS(Path path) {
		path = JMOptional.getOptionalIfTrue(path, JMPath.NotExistFilter)
				.flatMap(JMPath::createTempFilePathAsOpt).orElse(path);
		Icon iconInOS = os.getIcon(path.toFile());
		BufferedImage bufferedImage = new BufferedImage(iconInOS.getIconWidth(),
				iconInOS.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
		iconInOS.paintIcon(null, bufferedImage.getGraphics(), 0, 0);
		return bufferedImage;
	}

	public BufferedImage getCachedBufferedImageOfIconInOS(Path path) {
		return getSpecialPathAsOpt(path)
				.map(getCachedBufferedImageFunction(path))
				.orElseGet(() -> buildCachedBufferedImageOfFileIconInOS(path));
	}

	private Optional<String> getSpecialPathAsOpt(Path path) {
		return JMOptional
				.getOptionalIfTrue(path, JMPath.DirectoryFilter
						.or(JMPath.SymbolicLinkFilter).or(JMPath.HiddenFilter))
				.map(Path::toString);
	}

	private BufferedImage buildCachedBufferedImageOfFileIconInOS(Path path) {
		return getFilePathExtentionKeyAsOpt(path)
				.map(getCachedBufferedImageFunction(path))
				.orElseGet(() -> bufferedImageCache.get(unknownFileName));
	}

	private Optional<String> getFilePathExtentionKeyAsOpt(Path path) {
		return JMPath.getPathExtentionAsOpt(path)
				.map(this::buildFileExtentionKey);
	}

}
