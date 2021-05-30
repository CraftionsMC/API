/**
 * @copyright (c) 2021 Ben Siebert. All rights resered.
 * @author Ben Siebert
 */
package net.craftions.api.networking;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;

public class NetUtils {

    public static void download(String url1, File dest) {
        try {
            URL url = new URL(url1);
            BufferedInputStream bis = new BufferedInputStream(url.openStream());
            FileOutputStream fis = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int count = 0;
            while ((count = bis.read(buffer, 0, 1024)) != -1)
                fis.write(buffer, 0, count);
            fis.close();
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
