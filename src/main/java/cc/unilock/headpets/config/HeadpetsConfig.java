package cc.unilock.headpets.config;

import org.quiltmc.config.api.ReflectiveConfig;
import org.quiltmc.config.api.annotations.Comment;
import org.quiltmc.config.api.values.TrackedValue;
import org.quiltmc.loader.api.config.v2.QuiltConfig;

public class HeadpetsConfig extends ReflectiveConfig {
	public static final HeadpetsConfig INSTANCE = QuiltConfig.create("headpets", "config", HeadpetsConfig.class);

	@Comment("Amount to heal petter (0 to disable)")
	public final TrackedValue<Float> petter_heal_amount = value(0.0F);
	@Comment("Amount to heal pettee (0 to disable)")
	public final TrackedValue<Float> pettee_heal_amount = value(2.0F);
}
