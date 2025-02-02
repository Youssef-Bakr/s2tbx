package org.esa.s2tbx.dataio.s2.l2hf;

import org.esa.s2tbx.dataio.s2.S2Config;
import org.esa.s2tbx.dataio.s2.VirtualPath;

import java.io.IOException;

/**
 * Created by obarrile on 14/06/2016.
 */
public class L2hfUtils {

    public static boolean checkGranuleSpecificFolder(VirtualPath pathGranule, String specificFolder) {

        if (specificFolder.equals("Multi"))
            return true;
        VirtualPath rootPath = pathGranule.getParent();
        VirtualPath imgFolder = rootPath.resolve("IMG_DATA");
        VirtualPath[] paths;
        try {
            paths = imgFolder.listPaths();
        } catch (IOException e) {
            paths = null;
        }

        if (paths != null) {
            for (VirtualPath imgData : paths) {
                if (imgData.existsAndHasChildren()) {
                    if (imgData.getFileName().toString().equals("NATIVE")) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean checkMetadataSpecificFolder(VirtualPath pathMetadata, String specificFolder) {

        if (specificFolder.equals("Multi"))
            return true;
        VirtualPath rootPath = pathMetadata.getParent();
        VirtualPath granuleFolder = rootPath.resolve("GRANULE");
        VirtualPath[] paths;
        try {
            paths = granuleFolder.listPaths();
        } catch (IOException e) {
            paths = null;
        }

        if (paths != null) {
            for (VirtualPath granule : paths) {
                if (granule.existsAndHasChildren()) {

                    VirtualPath internalGranuleFolder = granule.resolve("IMG_DATA");
                    VirtualPath[] files2 ;
                    try {
                        files2 = internalGranuleFolder.listPaths();
                    } catch (IOException e) {
                        files2 = null;
                    }
                    if (files2 != null) {
                        for (VirtualPath imgData : files2) {
                            if (imgData.existsAndHasChildren()) {
                                if (imgData.getFileName().toString().equals("NATIVE")) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public static S2Config.Sentinel2ProductMission getMissionID(VirtualPath path) {
        if(path.getFullPathString().contains("S2A"))
            return S2Config.Sentinel2ProductMission.S2A;
        else if(path.getFullPathString().contains("S2B"))
            return S2Config.Sentinel2ProductMission.S2B;
        else if(path.getFullPathString().contains("LS8"))
            return S2Config.Sentinel2ProductMission.LS8;
        else if(path.getFullPathString().contains("LS9"))
            return S2Config.Sentinel2ProductMission.LS9;
        else
            return S2Config.Sentinel2ProductMission.UNKNOWN;
    }
}
