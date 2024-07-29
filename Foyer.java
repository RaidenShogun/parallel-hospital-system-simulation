// name : Ronald Shi studentID : 1547056

//import java.util.*;

// simulate foyer
public class Foyer {
    //Queue<Patient> wait_q;
    //Queue<Patient> leave_q;
    private Patient patientWaitInFoyer = null;  //patient wait to be lead to triage
    private Patient patientToLeave = null;   //patient wait to leave
    public Foyer(){
        //wait_q = new LinkedList<>();
        //leave_q = new LinkedList<>();
    }

    // only one patient can arrive in foyer, if there is one patient in foyer, Producer won't keep producing patient
    public synchronized void arriveAtED(Patient patient){
        //wait_q.add(patient);
        //notifyAll();
        while(patientWaitInFoyer != null){
            try{
                wait();
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
        patientWaitInFoyer = patient;  // this is patient is waiting in foyer
        System.out.printf("%s admitted to ED %n", patient.toString());
        notifyAll();
    }

    // nurse serving patient
    public synchronized Patient serve(){
        /*while(wait_q.isEmpty()){
            try{
                wait();
            }catch (InterruptedException e){}
        }
        return wait_q.poll();*/

        //make sure only one nurse can serve this patient
        while(patientWaitInFoyer == null || patientWaitInFoyer.allocated){
            try{
                wait();
            }catch(InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
        Patient patient = patientWaitInFoyer;
        //patientWaitInFoyer = null;
        patient.allocated = true;  //allocate this patient to a nurse
        notifyAll();
        return patient;
    }

    // lead this patient to triage, so foyer is empty now
    public synchronized void foyerIsEmpty(){
        this.patientWaitInFoyer = null;
        notifyAll();
    }

    // patient departs from ED
    public synchronized void departFromED(){
        while(patientToLeave == null){  // if there is patient wait to be discharged
            try{
                wait();
            }catch(InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
        System.out.printf("%s  discharged from ED%n", patientToLeave.toString());
        patientToLeave = null;
        notifyAll();
    }


    // let one patient wait to be discharged
    public synchronized void prepareToDischarge(Patient patient){
        while(patientToLeave != null){
            try{
                wait();
            }catch(InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
        patientToLeave = patient;  // this patient is ready to leave
        notifyAll();
    }
}
