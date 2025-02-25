package org.firstinspires.ftc.teamcode.tuning.Position;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Constants;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.FConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.LConstants;

import java.util.ArrayList;

@Config
@TeleOp
public class DrivePPTuning extends OpMode {

    private Follower follower;

    public static int poseCount = 3;

    public static double[] xPoses = new double[10];
    public static double[] yPoses = new double[10];
    public static double[] headingPoses = new double[10];

    @Override
    public void init() {
        Constants.setConstants(FConstants.class, LConstants.class);
        follower = new Follower(hardwareMap);
    }

    @Override
    public void loop() {
        telemetry.addData("Pose count", poseCount);
        for (int i = 0; i < poseCount; i++) {
            telemetry.addData("Pose " + i, "x: %.2f, y: %.2f, heading: %.2f", xPoses[i], yPoses[i], headingPoses[i]);
        }
        telemetry.update();
    }
    public PathChain curve(Follower follower) {
        ArrayList<Point> controlPoints = new ArrayList<>();

        Pose[] poses = new Pose[poseCount];
        for (int i = 0; i < poseCount; i++) {
            poses[i] = new Pose(xPoses[i], yPoses[i], headingPoses[i]);
        }

        for (Pose pose : poses) {
            controlPoints.add(new Point(pose.getX(), pose.getY(), 1));
        }

        BezierCurve bezierCurve = new BezierCurve(controlPoints);

        return follower.pathBuilder()
                .addPath(bezierCurve)
                .setLinearHeadingInterpolation(poses[0].getHeading(), poses[poses.length - 1].getHeading())
                .build();
    }
}