// name : Ronald Shi studentID : 1547056

//import java.util.*;

//Implementation of Nurse class, simulate actions of nurses

public class Nurse extends Thread{
    private int id;
    private Foyer foyer;
    private Triage triage;
    private Orderlies orderlies;
    private Treatment treatment;

    public Nurse(int id, Foyer foyer, Triage triage, Orderlies orderlies, Treatment treatment){
        this.id = id;
        this.foyer = foyer;
        this.triage = triage;
        this.orderlies = orderlies;
        this.treatment = treatment;
    }

    public String toString() {      // a toStirng method so that it's easy to print nurse's behavior
        String s = "Nurse " + id;
        return s;
    }

    public void run(){
        while(!isInterrupted()){
            try{
                Patient patient = foyer.serve();
                System.out.printf("%s allocated to %s%n", patient.toString(), this.toString());
                orderlies.obtain(this);  //gather orderlies
                //System.out.printf("%s recruits 3 orderlies (%d free)%n", this.toString(), orderliesLeft);
                transferToTriage(patient);
                //foyer.foyerIsEmpty();
                //System.out.printf("%s leaves Foyer%n", patient.toString());
                //transfer();

                triage.arriveInTriage(patient);
                orderlies.returnOrderlies(this);   //release orderlies after patient get into triage
                triageAssess();
                //System.out.printf("Nurse %d and %s are in triage %n", this.id, patient.toString());

                //if a patient is severe then simulate the treatment
                if (patient.Severe()) {
                    orderlies.obtain(this);  //gather orderlies to transfer patient to treatment
                    transferToTreatment(patient);
                    orderlies.returnOrderlies(this);   //send patient to the entrance of treatment room so release orderlies

                    treatment.patientArraive(patient, this); //nurse will wait patient to be treated

                    orderlies.obtain(this);  //gather orderlies to transfer patient to foyer
                    System.out.printf("%s leaves treatment%n", patient.toString());
                    treatment.leaveTreatment();
                }
                //else send patient back to foyer
                else{
                    //System.out.printf("Nurse %d and %s are leaving triage %n", this.id, patient.toString());
                    orderlies.obtain(this);   //gather orderlies to transfer patient to foyer
                    System.out.printf("%s leaves triage%n", patient.toString());
                    triage.releaseTriage();
                }

                orderlies.returnOrderlies(this);
                foyer.prepareToDischarge(patient);  //prepare to be discharged
                foyer.departFromED();
                sleep(Params.departurePause());  //this nurse needs a rest
            }
            catch (InterruptedException e){
                this.interrupt();
            }
        }
    }

    //a method simulate transfer to triage
    public void transferToTriage(Patient patient){
        foyer.foyerIsEmpty();
        System.out.printf("%s leaves Foyer%n", patient.toString());
        try{
            sleep(Params.TRANSFER_TIME);
        }catch (InterruptedException e){this.interrupt();}
    }

    //a method simulate patient assessed in triage
    public void triageAssess(){
        try{
            sleep(Params.TRIAGE_TIME);
        }catch (InterruptedException e){this.interrupt();}
    }

    //a method simulate transfer to treatment
    public void transferToTreatment(Patient patient){
        triage.releaseTriage();  //patient leaves triage and release semaphore
        System.out.printf("%s leaves triage%n", patient.toString());
        try{
            sleep(Params.TRANSFER_TIME);
        }catch (InterruptedException e){this.interrupt();}
    }

}
