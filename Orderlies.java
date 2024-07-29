// name : Ronald Shi studentID : 1547056

// a class simulate orderlies
public class Orderlies
{
    private int COUNT = Params.ORDERLIES;  //total number of orderlies

    //gather orderlies, needs to check whether orderlies is enough
    public synchronized void obtain(Nurse nurse) throws InterruptedException {
        while (true)
        {
            synchronized (this){
                if(COUNT-Params.TRANSFER_ORDERLIES < 0){
                    try{
                        wait();
                    }catch (InterruptedException e){Thread.currentThread().interrupt();}
                }else{
                    COUNT-=Params.TRANSFER_ORDERLIES;
                    System.out.printf("%s recruits %d orderlies (%d free)%n", nurse.toString(), Params.TRANSFER_ORDERLIES, COUNT);
                    break;
                }
            }
        }
    }

    //return orderlies
    public synchronized void returnOrderlies(Nurse nurse){
        COUNT+=Params.TRANSFER_ORDERLIES;
        System.out.printf("%s  releases %d orderlies (%d free)%n", nurse.toString(), Params.TRANSFER_ORDERLIES, COUNT);
        notifyAll();
    }
}
