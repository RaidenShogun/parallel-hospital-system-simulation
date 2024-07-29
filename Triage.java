// name : Ronald Shi studentID : 1547056

import java.util.concurrent.Semaphore;


//simulation of triage
public class Triage {

    private final Semaphore triageSemaphore = new Semaphore(1);  //use a semaphore to make sure only one patient in triage

    //patient arrives in triage
    public void arriveInTriage(Patient patient) throws InterruptedException{
        triageSemaphore.acquire();
        try{
            System.out.printf("%s enters triage %n", patient.toString());
            Thread.sleep(Params.TRIAGE_TIME);
        }
        finally {}
    }

    //release semaphore, triage is empty now
    public void releaseTriage() {
        triageSemaphore.release();
    }
}
