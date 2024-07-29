// name : Ronald Shi studentID : 1547056

// simulation of specialist
public class Specialist extends Thread {
    private Treatment treatment;

    private static final int STAY_TIME = 500; // Specialist's stay time in the treatment room

    public Specialist(Treatment treatment) {
        this.treatment = treatment;
    }

    public void run() {
        while (!isInterrupted()) {
            try {
                // Simulate the Specialist's duties elsewhere
                Thread.sleep(Params.SPECIALIST_AWAY_TIME);
                treatment.specialistReturns();

                // Start to treat patient if there is one
                treatment.treatPatient(STAY_TIME);

                // Specialist leaves the Treatment
                treatment.specialistLeaves();
            } catch (InterruptedException e) {
                this.interrupt();
            }
        }
    }
}


