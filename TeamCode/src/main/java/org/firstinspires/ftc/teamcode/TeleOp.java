package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp
public class TeleOp extends OpMode {
    double x1, x2, y1;

    public Util util;


    // This is called when you press the "init" button on the driver's hub
    @Override
    public void init() {
        util = new Util();
        util.init(hardwareMap);
    }

    // This is originally called when the "init" button is pressed on the
    // driver's hub and repeated
    @Override
    public void init_loop() {
        telemetry.addData("limelight status", util.limelightStatus());
        telemetry.update();
    }

    // Repeatedly runs the code inside during the start button
    @Override
    public void loop() {
        if (Math.abs(gamepad1.left_stick_y) > 0.2) {
            y1 = -gamepad1.left_stick_y;
        } else {
            y1 = 0;
        }
        if (Math.abs(gamepad1.left_stick_x) > 0.2) {
            x1 = gamepad1.left_stick_x;
        } else {
            x1 = 0;
        }
        if (Math.abs(gamepad1.right_stick_x) > 0.2) {
            x2 = gamepad1.right_stick_x;
        } else {
            x2 = 0;
        }
        util.drive(y1, x1, x2);
        telemetry.update();
        // Harrison - Went in and fixed the formatting
        // of the code and removed original setPower's 9/28/26
    }
}
