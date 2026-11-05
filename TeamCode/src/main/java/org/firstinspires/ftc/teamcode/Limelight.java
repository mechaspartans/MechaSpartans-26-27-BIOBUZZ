package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Utility;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
@Utility(name = "LIMELIGHT")
public class Limelight extends LinearOpMode {

    private Limelight3A limelight;

    @Override
    public void runOpMode() throws InterruptedException {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");

        // Starts polling for data.
        limelight.start();
        telemetry.addData("status", "Waiting for start");
        telemetry.update();
        waitForStart();
        telemetry.addData("status", "Starting...");
        telemetry.update();
        while (opModeIsActive()) {
            // Checks to see what the data from the limelight is.
            LLResult result = limelight.getLatestResult();
            if (result != null) {
                if (result.isValid()) {
                    Pose3D botpose = result.getBotpose();
                    telemetry.addData("tx", result.getTx());
                    telemetry.addData("ty", result.getTy());
                    telemetry.addData("Botpose", botpose.toString());
                    telemetry.update();
                }
                telemetry.addData("status", "Result is invalid");
                telemetry.update();
            }
            telemetry.addData("status", "Result is null");
            telemetry.update();
        }
    }
}
