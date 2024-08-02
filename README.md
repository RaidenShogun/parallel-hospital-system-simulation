# What is it?
A simple simulation system that mimic the whole process of a treatment, used to detect whether there is a deadlock if resource is insufficient in hospital.
## Classes
**Producer** ：Produce patients if foyer is empty.

**Consumer** : Discharge a patient ready to leave foyer.

**Foyer** : The entry area of the ED where patients are admitted and initially assessed, only one patient can wait here.

**Triage** : The process where patients are examined by a nurse to assess the severity of their condition and determine the appropriate treatment pathway, only one patient be assessed here.

**Treatment** : The area where patients with severe conditions are treated by the specialist, only one patient can be treated here.

**Nurse** : Medical professionals assigned to patients upon their arrival, responsible for taking patients through the triage process, assisting with treatment, and eventually discharging them.

**Specialist** : A highly skilled medical professional who provides treatment to patients assessed as having severe conditions. Could leave treatment periodically and come back.

**Orderly** : Support staff who assist nurses in transferring patients between different locations within the ED (e.g., from the foyer to triage, or triage to treatment).
# How does it work?
1. Clone this project to your device.
2. cd to the directory you've downloaded this project, then type __javac *.java__ to compile java files.
3. Type __java Main__ to run
4. Check whether it will generate a deadlock with default parameters
