package cc.unilock.headpets.config;

import folk.sisby.kaleido.api.WrappedConfig;
import folk.sisby.kaleido.lib.quiltconfig.api.annotations.Comment;
import net.fabricmc.loader.api.FabricLoader;

public class HeadpetsConfig extends WrappedConfig {
	public static final HeadpetsConfig CONFIG = HeadpetsConfig.createToml(FabricLoader.getInstance().getConfigDir(), "headpets", "config", HeadpetsConfig.class);

	@Comment("Amount to heal petter (0 to disable)")
	public final Float petter_heal_amount = 0.0F;
	@Comment("Amount to heal pettee (0 to disable)")
	public final Float pettee_heal_amount = 2.0F;
}
