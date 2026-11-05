package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@Autonomous
public class OdometryCalibration extends OpMode {
    public DcMotorEx odopodLeft = null;
    public DcMotorEx odopodMiddle = null;
    public DcMotorEx odopodRight = null;

    @Override
    public void init() {
        odopodLeft = hardwareMap.get(DcMotorEx.class, "bL");
        odopodMiddle = hardwareMap.get(DcMotorEx.class, "bR");
        odopodRight = hardwareMap.get(DcMotorEx.class, "fR");
    }

    @Override
    public void loop() {
        telemetry.addData("Odo Pod Positions", "left: %d, middle: %d, right: %d",
                odopodLeft.getCurrentPosition(), odopodMiddle.getCurrentPosition(),
                odopodRight.getCurrentPosition());
    }
}
