package kr.jm.utils;

import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Supplier;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.swing.Icon;

import kr.jm.utils.datastructure.JMMap;
import kr.jm.utils.enums.OS;
import kr.jm.utils.helper.JMOptional;
import kr.jm.utils.helper.JMPath;

public class JMIconFactory {

	private static final String QUESTION = "?";
	private Map<String, BufferedImage> bufferedImageCache;
	private Map<String, Image> fxImageCache;

	public JMIconFactory() {
		this.bufferedImageCache = new ConcurrentHashMap<>();
		this.bufferedImageCache.put(QUESTION,
				buildBufferedImageOfIconInOS(JMPath.getPath(QUESTION)));
		this.fxImageCache = new ConcurrentHashMap<>();
		this.fxImageCache.put(QUESTION,
				buildFxImage(bufferedImageCache.get(QUESTION)));
	}

	private Function<? super String, ? extends BufferedImage> getCachedBufferedImageFunction(
			Path path) {
		Supplier<BufferedImage> newValueSupplier = () -> buildBufferedImageOfIconInOS(path);
		return key -> JMMap.getOrPutGetNew(bufferedImageCache, key,
				newValueSupplier);
	}

	private String buildFileExtentionKey(String extention) {
		return QUESTION + extention;
	}

	public BufferedImage buildBufferedImageOfIconInOS(Path path) {
		path = JMOptional.getOptionalIfTrue(path, JMPath.ExistFilter.negate())
				.flatMap(JMPath::createTempFilePathAsOpt).orElse(path);
		Icon iconInOS = OS.getOS().getIconInOS(path.toFile());
		BufferedImage bufferedImage = new BufferedImage(
				iconInOS.getIconWidth(), iconInOS.getIconHeight(),
				BufferedImage.TYPE_INT_ARGB);
		iconInOS.paintIcon(null, bufferedImage.getGraphics(), 0, 0);
		return bufferedImage;
	}

	public BufferedImage getCachedBufferedImageOfIconInOS(Path path) {
		return getSpecialPathAsOpt(path).map(
				getCachedBufferedImageFunction(path)).orElseGet(
				() -> buildCachedBufferedImageOfFileIconInOS(path));
	}

	private Optional<String> getSpecialPathAsOpt(Path path) {
		return JMOptional.getOptionalIfTrue(
				path,
				JMPath.DirectoryFilter.or(JMPath.SymbolicLinkFilter).or(
						JMPath.HiddenFilter)).map(Path::toString);
	}

	private BufferedImage buildCachedBufferedImageOfFileIconInOS(Path path) {
		return getFilePathExtentionKeyAsOpt(path).map(
				getCachedBufferedImageFunction(path)).orElseGet(
				() -> bufferedImageCache.get(QUESTION));
	}

	private Optional<String> getFilePathExtentionKeyAsOpt(Path path) {
		return JMPath.getPathExtentionAsOpt(path).map(
				this::buildFileExtentionKey);
	}

	public Image getFxImageOfIconInOS(Path path) {
		return getSpecialPathAsOpt(path).map(getCachedFxImageFunction(path))
				.orElseGet(() -> buildCachedFxImageOfFileIconInOS(path));
	}

	private Image buildCachedFxImageOfFileIconInOS(Path path) {
		return getFilePathExtentionKeyAsOpt(path).map(
				getCachedFxImageFunction(path)).orElseGet(
				() -> fxImageCache.get(QUESTION));
	}

	private Function<? super String, ? extends Image> getCachedFxImageFunction(
			Path path) {
		Supplier<Image> newValueSupplier = () -> buildFxImage(getCachedBufferedImageOfIconInOS(path));
		return key -> JMMap.getOrPutGetNew(fxImageCache, key, newValueSupplier);
	}

	public Image buildFxImage(BufferedImage bufferedImage) {
		return SwingFXUtils.toFXImage(bufferedImage, null);
	}

	public ImageView buildImageView(Image fxImage) {
		return new ImageView(fxImage);
	}

	public ImageView buildImageView(BufferedImage bufferedImage) {
		return buildImageView(buildFxImage(bufferedImage));
	}

	public ImageView getImageViewOfIconInOS(Path path) {
		return JMOptional.getOptionalIfTrue(path, JMPath.HiddenFilter)
				.map(this::buildImageViewAppliedDefaultOpacity)
				.orElseGet(() -> buildImageView(path));
	}

	public ImageView buildImageViewAppliedDefaultOpacity(Path path,
			double opacityValue) {
		return applyOpacity(buildImageView(path), opacityValue);
	}

	public ImageView buildImageViewAppliedDefaultOpacity(Path path) {
		return buildImageViewAppliedDefaultOpacity(path, 0.5);
	}

	public ImageView buildImageView(Path path) {
		return buildImageView(getFxImageOfIconInOS(path));
	}

	public ImageView applyOpacity(ImageView imageView, double opacityValue) {
		imageView.setOpacity(opacityValue);
		return imageView;
	}
}
