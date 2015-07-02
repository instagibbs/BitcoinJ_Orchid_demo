import com.subgraph.orchid.TorClient;
import org.bitcoinj.core.PeerAddress;
import org.bitcoinj.core.PeerGroup;
import org.bitcoinj.params.MainNetParams;
import java.net.InetAddress;
import java.net.InetSocketAddress;

public class demo
{
    public static void main(String [] args)
    {
        PeerGroup peerGroup = null;
        try {
                peerGroup = PeerGroup.newWithTor(MainNetParams.get(), null, new TorClient());
            }catch (Exception e){
                e.printStackTrace();
                System.exit(0);
        }
        try{
            PeerAddress OnionAddr = new PeerAddress(InetAddress.getLocalHost(), 8333) {
                        public InetSocketAddress toSocketAddress() {
                            return InetSocketAddress.createUnresolved("hhiv5pnxenvbf4am.onion", 8333);
                        }
                    };
            peerGroup.addAddress(OnionAddr);
            System.out.println("Starting Tor connection");
            peerGroup.start();
            System.out.println("Waiting for peer");
            peerGroup.waitForPeers(1).get();
            System.out.println("Peers connected: " + peerGroup.getConnectedPeers());
            System.out.println(peerGroup.getConnectedPeers().get(0).getPeerVersionMessage().subVer);
            System.exit(0);
        }catch (Exception e){
            System.out.println("CRAP");
            e.printStackTrace();
        }
    }
}
