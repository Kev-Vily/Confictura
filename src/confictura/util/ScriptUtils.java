package confictura.util;

import rhino.*;

import static arc.Core.*;
import static confictura.ConficturaMod.*;
import static mindustry.Vars.*;

/**
 * Common utility bridge for JavaScript codes.
 * @author GlennFolker
 */
public final class ScriptUtils{
    public static Context context;
    public static ImporterTopLevel vanillaScope, modScope;

    private ScriptUtils(){
        throw new AssertionError();
    }

    /** Initializes the utility bridge. Main-thread only! */
    public static void init(){
        var scripts = mods.getScripts();

        context = scripts.context;
        vanillaScope = (ImporterTopLevel)scripts.scope;
        modScope = new ImporterTopLevel(context);

        context.evaluateString(modScope, files.internal("scripts/global.js").readString(), "global.js", 1);
    }

    /**
     * Imports packages defined by this mod into the script scope.
     * @param scope {@link #vanillaScope} for the base game's script scope (mod scripts folder and console), or
     *              {@link #modScope} for scope specifically used by this mod.
     */
    public static void importDefaults(ImporterTopLevel scope){
        for(var name : packages) importPackage(scope, name);
    }

    /**
     * Imports a single package to the given scope.
     * @param scope See {@link #importDefaults(ImporterTopLevel)}.
     * @param name  The package's fully qualified name.
     */
    public static void importPackage(ImporterTopLevel scope, String name){
        var p = new NativeJavaPackage(name, mods.mainLoader());
        p.setParentScope(scope);

        scope.importPackage(p);
    }
}
