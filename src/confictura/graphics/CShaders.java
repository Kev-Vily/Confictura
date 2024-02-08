package confictura.graphics;

import arc.files.*;
import arc.graphics.gl.*;
import confictura.graphics.shaders.*;
import mindustry.*;

import static mindustry.Vars.*;

/**
 * Defines the {@link Shader shaders}s this mod offers.
 * @author GlennFolker
 */
public final class CShaders{
    public static DepthShader depth;
    public static DepthAtmosphereShader depthAtmosphere;
    public static PortalForcefieldShader portalForcefield;

    public static PlanetDebugShader planetDebug;

    private CShaders(){
        throw new AssertionError();
    }

    /** Loads the shaders. Client-side and main thread only! */
    public static void load(){
        depth = new DepthShader();
        depthAtmosphere = new DepthAtmosphereShader();
        portalForcefield = new PortalForcefieldShader();

        planetDebug = new PlanetDebugShader();
    }

    /**
     * Resolves shader files from this mod via {@link Vars#tree}.
     * @param name The shader file name, e.g. {@code my-shader.frag}.
     * @return     The shader file, located inside {@code shaders/confictura/}.
     */
    public static Fi file(String name){
        return tree.get("shaders/confictura/" + name);
    }
}
