// name : Ronald Shi studentID : 1547056

// a class simulate treatment
public class Treatment {
    private Patient patientWaiting = null; // Track if a patient is waiting for treatment

    // if a patient arrives treatment, then this is the only patient needs to be treated now
    public synchronized void patientArraive(Patient patient, Nurse nurse){  //patient arrive in treatment and nurse thread waits for patient's treatment done
        while(patientWaiting != null){
            try{
                wait();
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
        patientWaiting = patient;
        System.out.printf("%s enters treatment room %n", patient.toString());
        waitTreatmentDone(patientWaiting);  //wait for the treatment
    }

    // nurse will wait treatment is done
    public void waitTreatmentDone(Patient patient){
        while(!patient.treated){
            try{
                wait();
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
    }

    //simulate a specialist to treat patient

    public synchronized void treatPatient(int STAY_TIME){
        int turn = 1;  // a variable to track how many STAY_TIME that a specialist wait
        while(patientWaiting == null && turn < 2){   //Specialist will only wait for patient in one STAY_TIME
            try{
                wait(STAY_TIME);
            }catch (InterruptedException e){Thread.currentThread().interrupt();}
            turn ++;
        }

        //if there is a patient arrives then start treating, otherwise specialist leaves treatment
        if(patientWaiting != null){
            System.out.printf("%s treatment started %n", patientWaiting.toString());
            try{
                Thread.sleep(Params.TREATMENT_TIME);
            }catch (InterruptedException e){Thread.currentThread().interrupt();}

            patientWaiting.treated = true;  //patient treated
            System.out.printf("%s  treatment complete %n", patientWaiting.toString());
            notifyAll();
        }

    }

    // patient leaves
    public synchronized void leaveTreatment(){
        patientWaiting = null;
        notifyAll();
    }


    public synchronized void specialistReturns() {
        System.out.println("Specialist enters treatment room.");
    }

    public synchronized void specialistLeaves() {
        System.out.println("Specialist leaves treatment room.");
    }
}
