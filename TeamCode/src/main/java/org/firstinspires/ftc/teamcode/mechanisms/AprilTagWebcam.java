package org.firstinspires.ftc.teamcode.mechanisms;

import android.util.Size;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagClusterDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.apriltag.AprilTagSingleDetection;

import java.util.ArrayList;
import java.util.List;

public class AprilTagWebcam {

    private AprilTagProcessor aprilTagProcessor;

    private VisionPortal visionPortal;

    private List<AprilTagDetection> telemetryId = new ArrayList<>();

    private Telemetry telemetry;

    public void init(HardwareMap hwMap, Telemetry telemetry) {

        this.telemetry = telemetry;

        aprilTagProcessor = new AprilTagProcessor.Builder()
                .setDrawTagID(true)
                .setDrawTagOutline(true)
                .setDrawAxes(true)
                .setDrawCubeProjection(true)
                .setOutputUnits(DistanceUnit.CM, AngleUnit.DEGREES)
                .build();

        VisionPortal.Builder builder = new VisionPortal.Builder();
        builder.setCamera(hwMap.get(WebcamName.class, "Webcam 1"));
        builder.setCameraResolution(new Size(800, 600));
        builder.addProcessor(aprilTagProcessor);

        visionPortal = builder.build();

    }

    public void update() {
        telemetryId = aprilTagProcessor.getDetections();
        telemetry.update();
    }

    public List<AprilTagDetection> getDetectedTags() {
        return telemetryId;
    }

    public AprilTagSingleDetection getTagBySpecificSingleId(int id) {
        for (AprilTagDetection detection : telemetryId) {
            if (detection instanceof AprilTagSingleDetection) {
                AprilTagSingleDetection singleDet = (AprilTagSingleDetection) detection;
                if (singleDet.id == id) {
                    return singleDet;
                }
            }
        }
        return null;
    }

    public AprilTagClusterDetection getTagBySpecificClusterName(String name) {
        for (AprilTagDetection detection : telemetryId) {
            if (detection instanceof AprilTagClusterDetection) {
                AprilTagClusterDetection clusterDet = (AprilTagClusterDetection) detection;
                if (clusterDet.metadata.name == name) {
                    return clusterDet;
                }
            }
        }
        return null;
    }

    public void displayDetectionTelemetry(String wantedName, int wantedId) {
        if (wantedName == null) {return;}

        if (getTagBySpecificSingleId(wantedId) != null) {
            AprilTagSingleDetection telemetryId = getTagBySpecificSingleId(wantedId);
            if (telemetryId.metadata != null) {
                telemetry.addLine(String.format("\n==== (ID %d) %s", telemetryId.id, telemetryId.metadata.name));
                telemetry.addLine(String.format("XYZ %6.1f %6.1f %6.1f  (inch)", telemetryId.ftcPose.x, telemetryId.ftcPose.y, telemetryId.ftcPose.z));
                telemetry.addLine(String.format("PRY %6.1f %6.1f %6.1f  (deg)", telemetryId.ftcPose.pitch, telemetryId.ftcPose.roll, telemetryId.ftcPose.yaw));
                telemetry.addLine(String.format("RBE %6.1f %6.1f %6.1f  (inch, deg, deg)", telemetryId.ftcPose.range, telemetryId.ftcPose.bearing, telemetryId.ftcPose.elevation));
            } else {
                telemetry.addLine(String.format("\n==== (ID %d) Unknown", telemetryId.id));
                telemetry.addLine(String.format("Center %6.0f %6.0f   (pixels)", telemetryId.center.x, telemetryId.center.y));
            }
        } else if (getTagBySpecificClusterName(wantedName) != null) {
            AprilTagClusterDetection telemetryId = getTagBySpecificClusterName(wantedName);
            if (telemetryId.metadata == null) {return;}
            telemetry.addLine(String.format("\n==== Tag Cluster (%s)", telemetryId.metadata.name));
            telemetry.addLine(String.format("Percent tags found: %d", telemetryId.percentClusterFound));
            telemetry.addLine(String.format("XYZ %6.1f %6.1f %6.1f  (inch)", telemetryId.ftcPose.x, telemetryId.ftcPose.y, telemetryId.ftcPose.z));
            telemetry.addLine(String.format("PRY %6.1f %6.1f %6.1f  (deg)", telemetryId.ftcPose.pitch, telemetryId.ftcPose.roll, telemetryId.ftcPose.yaw));
            telemetry.addLine(String.format("RBE %6.1f %6.1f %6.1f  (inch, deg, deg)", telemetryId.ftcPose.range, telemetryId.ftcPose.bearing, telemetryId.ftcPose.elevation));
        } else {
            return;
        }
    }

    public void stop() {
        if (visionPortal != null) {
            visionPortal.close();
        }
    }

}
