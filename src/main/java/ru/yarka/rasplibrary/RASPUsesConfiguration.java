package ru.yarka.rasplibrary;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.MissingResourceException;

public class RASPUsesConfiguration {

    private List<InetAddress> accessList = Collections.emptyList();

    private RASPUsesConfiguration() {
    }

    public static RASPUsesConfiguration emptyConfiguration() {
        return new RASPUsesConfiguration();
    }

    public static RASPUsesConfiguration fromDefault() throws IOException {
        Path path = Paths.get(".", "config.json");
        if(Files.notExists(path)) {
            InputStream in = RASPUsesConfiguration.class.getClassLoader().getResourceAsStream("config.json");
            if(in != null) Files.copy(in, path);
            else throw new MissingResourceException("config.json not found", RASPUsesConfiguration.class.getName(), "Missing");
        }

        return fromString(Files.readString(path));
    }

    public static RASPUsesConfiguration fromString(String data) {
        JSONObject jsonObject = new JSONObject(data);
        return new RASPUsesConfiguration();
    }

    public List<InetAddress> getAccessList() {
        return accessList;
    }

    public void setAccessList(List<InetAddress> accessList) {
        this.accessList = accessList;
    }

    public boolean hasAccessList() {
        return !accessList.isEmpty();
    }
}
