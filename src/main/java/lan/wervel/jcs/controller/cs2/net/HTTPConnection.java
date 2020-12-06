/*
 * Copyright (C) 2020 fransjacobs.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package lan.wervel.jcs.controller.cs2.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import org.pmw.tinylog.Logger;

/**
 *
 * @author fransjacobs
 */
public class HTTPConnection {

    private final InetAddress cs2Address;

    private final static String HTTP = "http://";
    private final static String CONFIG = "/config/";
    private final static String LOCOMOTIVE = "lokomotive.cs2";
    private final static String MAGNETARTIKEL = "magnetartikel.cs2";

    HTTPConnection(InetAddress cs2Address) {
        this.cs2Address = cs2Address;
    }

    public String getLocomotivesFile() {
        StringBuilder locs = new StringBuilder();
        try {
            URL cs2 = new URL(HTTP + cs2Address.getHostAddress() + CONFIG + LOCOMOTIVE);
            URLConnection lc = cs2.openConnection();
            try (BufferedReader in = new BufferedReader(new InputStreamReader(lc.getInputStream()))) {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    locs.append(inputLine.strip());
                    locs.append("\n");
                }
            }

        } catch (MalformedURLException ex) {
            Logger.error(ex);
        } catch (IOException ex) {
            Logger.error(ex);
        }
        return locs.toString();
    }

    public String getAccessoriesFile() {
        StringBuilder locs = new StringBuilder();
        try {
            URL cs2 = new URL(HTTP + cs2Address.getHostAddress() + CONFIG + MAGNETARTIKEL);
            URLConnection lc = cs2.openConnection();
            try (BufferedReader in = new BufferedReader(new InputStreamReader(lc.getInputStream()))) {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    locs.append(inputLine.strip());
                    locs.append("\n");
                }
            }

        } catch (MalformedURLException ex) {
            Logger.error(ex);
        } catch (IOException ex) {
            Logger.error(ex);
        }
        return locs.toString();
    }

//    public static void main(String[] args) throws Exception {
//        Configurator.defaultConfig().level(org.pmw.tinylog.Level.TRACE).activate();
//        InetAddress inetAddr = InetAddress.getByName("192.168.1.126");
//        HTTPConnection hc = new HTTPConnection(inetAddr);
//        String loks = hc.getLocomotivesFile();
//        LocomotiveParser lp = new LocomotiveParser();
//        List<Locomotive> locList = lp.parseLocomotivesFile(loks);
//
//        for (Locomotive loc : locList) {
//            System.out.println(loc.toLogString());
//        }
//        String accessories = hc.getAccessoriesFile();
//        AccessoryParser ap = new AccessoryParser();
//        List<SolenoidAccessory> acList = ap.parseAccessoryFile(accessories);
//
//        for (SolenoidAccessory sa : acList) {
//            System.out.println(sa.toLogString());
//        }
//    }

}
