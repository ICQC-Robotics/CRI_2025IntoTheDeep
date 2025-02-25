package org.firstinspires.ftc.teamcode.tuning.Position;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoImplEx;
import org.firstinspires.ftc.teamcode.Const;

@Config
@TeleOp
public class ServoTuning extends OpMode {
    public static Servo outtakeRot, outtakeDistRight, outtakeDistLeft, intakeRot, outtakeClaw, outtakeTwist, shifter;

    public static double outtakeRotPos = 0.5, outtakeDistRightPos = 0.5, outtakeDistLeftPos = 0.5;
    public static double intakeRotPos = 0.5, outtakeClawPos = 0.5, outtakeTwistPos = 0.5;
    public static double shifterPos = 0.2;

    @Override
    public void init() {
        outtakeRot = hardwareMap.get(Servo.class, Const.outtakeRot);
        outtakeDistRight = hardwareMap.get(Servo.class, Const.outtakeDistRight);
        outtakeDistLeft = hardwareMap.get(Servo.class, Const.outtakeDistLeft);
        intakeRot = hardwareMap.get(Servo.class, Const.intakeRot);
        outtakeClaw = hardwareMap.get(Servo.class, Const.outtakeClaw);
        outtakeTwist = hardwareMap.get(Servo.class, Const.outtakeTwist);
        shifter = hardwareMap.get(Servo.class, Const.gearShifter);

        Servo[] servos = {outtakeRot, outtakeDistRight, outtakeDistLeft, intakeRot, outtakeClaw, outtakeTwist, shifter};
        for (Servo servo : servos) {
            ((ServoImplEx) servo).setPwmRange(new PwmControl.PwmRange(500, 2500, 5000));
        }
    }

    @Override
    public void loop() {
        outtakeRot.setPosition(Math.max(0, Math.min(1, outtakeRotPos)));
        outtakeDistRight.setPosition(Math.max(0, Math.min(1, outtakeDistRightPos)));
        outtakeDistLeft.setPosition(Math.max(0, Math.min(1, outtakeDistLeftPos)));
        intakeRot.setPosition(Math.max(0, Math.min(1, intakeRotPos)));
        outtakeClaw.setPosition(Math.max(0, Math.min(1, outtakeClawPos)));
        outtakeTwist.setPosition(Math.max(0, Math.min(1, outtakeTwistPos)));
        shifter.setPosition(Math.max(0, Math.min(1, shifterPos)));

        telemetry.addData("Outtake Rot Position", outtakeRot.getPosition());
        telemetry.addData("Outtake Dist Right Position", outtakeDistRight.getPosition());
        telemetry.addData("Outtake Dist Left Position", outtakeDistLeft.getPosition());
        telemetry.addData("Intake Rot Position", intakeRot.getPosition());
        telemetry.addData("Outtake Claw Position", outtakeClaw.getPosition());
        telemetry.addData("Outtake Twist Position", outtakeTwist.getPosition());
        telemetry.addData("Shifter Position", shifter.getPosition());

        telemetry.update();
    }
}
