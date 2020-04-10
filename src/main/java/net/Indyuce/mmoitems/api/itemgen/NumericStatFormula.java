package net.Indyuce.mmoitems.api.itemgen;

import java.util.Random;

import org.apache.commons.lang.Validate;
import org.bukkit.configuration.ConfigurationSection;

import net.Indyuce.mmoitems.stat.data.DoubleData;
import net.Indyuce.mmoitems.stat.data.type.StatData;

public class NumericStatFormula implements RandomStatData {
	private final double base, scale, spread, maxSpread;

	private static final Random random = new Random();

	public static final NumericStatFormula ZERO = new NumericStatFormula(0, 0, 0, 0);

	public NumericStatFormula(Object object) {
		Validate.notNull(object, "Config must not be null");

		if (object instanceof Number) {
			base = Double.valueOf(object.toString());
			scale = 0;
			spread = 0;
			maxSpread = 0;
			return;
		}

		if (object instanceof ConfigurationSection) {
			ConfigurationSection config = (ConfigurationSection) object;
			base = config.getDouble("base");
			scale = config.getDouble("scale");
			spread = config.getDouble("spread");
			maxSpread = config.getDouble("max-spread");

			Validate.isTrue(spread >= 0, "Spread must be positive");
			Validate.isTrue(maxSpread >= 0, "Max spread must be positive");
			return;
		}

		throw new IllegalArgumentException("Must specify a config section or a number");
	}

	/*
	 * used as a StatData class to generate a DoubleData instance!
	 */
	public NumericStatFormula(double base, double scale, double spread, double maxSpread) {
		this.base = base;
		this.scale = scale;
		this.spread = spread;
		this.maxSpread = maxSpread;
	}

	public double getBase() {
		return base;
	}

	public double getScale() {
		return scale;
	}

	public double calculate(double x) {

		// calculate linear value
		double linear = base + scale * x;

		// apply gaussian distribution to add +- maxSpread%
		// spread represents the standard deviation in % of the calculated
		// linear value
		double gaussian = linear * (1 + Math.min(Math.max(random.nextGaussian() * spread, -maxSpread), maxSpread));

		return gaussian;
	}

	@Override
	public StatData randomize(GeneratedItemBuilder builder) {
		return new DoubleData(calculate(builder.getLevel()));
	}
}