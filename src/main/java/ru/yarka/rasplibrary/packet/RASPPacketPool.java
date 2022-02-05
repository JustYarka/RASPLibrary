package ru.yarka.rasplibrary.packet;

import ru.yarka.rasplibrary.packet.auth.RASPAuthRequestPacket;
import ru.yarka.rasplibrary.packet.auth.RASPSessionClosePacket;
import ru.yarka.rasplibrary.packet.control.RASPCustomDataPacket;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ConcurrentHashMap;

public class RASPPacketPool {

    private static ConcurrentHashMap<Byte, Class<? extends RASPIncomingPacket>> packets = new ConcurrentHashMap<>();

    public static void register(byte id, Class<? extends RASPIncomingPacket> packet) {
        packets.put(id, packet);
    }

    public static void registerDefault() {
        register(RASPPacketIdetifiers.RASP_AUTH_REQUEST.getNetworkId(), RASPAuthRequestPacket.class);
        register(RASPPacketIdetifiers.RASP_SOUT.getNetworkId(), RASPCustomDataPacket.class);
        register(RASPPacketIdetifiers.RASP_SESSION_CLOSE.getNetworkId(), RASPSessionClosePacket.class);
    }

    public static RASPIncomingPacket getPacket(byte id) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<? extends RASPIncomingPacket> pk = packets.getOrDefault(id, null);
        return pk != null ? pk.getDeclaredConstructor().newInstance() : null;
    }
}
