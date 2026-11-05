package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.mechanisms.AprilTagWebcam;

// Starter Bot name: Sean Baron

public class Util {

    public HardwareMap hwMap;

    public DcMotorEx frontRight;
    public DcMotorEx backRight;
    public DcMotorEx frontLeft;
    public DcMotorEx backLeft;
    public Limelight3A limelight;
    public AprilTagWebcam webcam;
    public GoBildaPinpointDriver pinpoint;
    //public DcMotorEx launcher = null;

    // Takes 3 inputs that sets the speed for each of the
    // wheels to drive forward, strafe, and turn.
    public void drive(double forward, double strafe, double turn) {
        double fR = forward - strafe - turn;
        double bR = -forward - strafe + turn;
        double fL = forward + strafe + turn;
        double bL = -forward + strafe - turn;

        frontRight.setPower(fR);
        backRight.setPower(bR);
        frontLeft.setPower(fL);
        backLeft.setPower(bL);
    }


    public void init(HardwareMap ahwMap) {
        hwMap = ahwMap;
        initMotors();
        initServos();
        initSensors();
        initCamera();
        pinpoint = ahwMap.get(GoBildaPinpointDriver.class, "pinpoint");
    }

    public void initMotors() {
        frontRight = hwMap.get(DcMotorEx.class, "fR");
        backRight = hwMap.get(DcMotorEx.class, "bR");
        frontLeft = hwMap.get(DcMotorEx.class, "fL");
        backLeft = hwMap.get(DcMotorEx.class, "bL");
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void initServos() {

    }

    public void initSensors() {

    }

    public void initCamera() {
        limelight = hwMap.get(Limelight3A.class, "limelight");
        limelight.start();
        // 0 = april, 1 = blue, 2 = red, 3 = yellow
        limelight.pipelineSwitch(0);
        webcam = hwMap.get(AprilTagWebcam.class, "Webcam");
    }

    public boolean limelightStatus() {
        return limelight.isConnected();
    }

    /*void launch (){
    * if (gamepad1.right_bumper) {
      *launcher.setVelocity(LAUNCHER_TARGET_VELOCITY);
       *telemetry.addData("Launcher", launcher.getVelocity());
        *}
        *  else {
        *   launcher.setVelocity(0);
     } this all will most likely not work.*/
}

