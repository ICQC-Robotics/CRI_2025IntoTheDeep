package org.firstinspires.ftc.teamcode.tuning.PID;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.Const;

@Config
@TeleOp
public class SingleSlideTuning extends OpMode {
    private PIDController controller;
    private Servo intake;

    public static double p = 0, i=0, d=0;
    public static double f = 0;

    public static double target = 0;
    private int pos;

    private DcMotorEx hSlide;

    @Override
    public void init() {
        intake = hardwareMap.get(Servo.class, Const.intakeRot);
        intake.setPosition(.58);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        hSlide = hardwareMap.get(DcMotorEx.class, Const.hSlide);
        hSlide.setDirection(DcMotorSimple.Direction.FORWARD);
        hSlide.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        hSlide.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        controller = new PIDController(p, i, d);
    }

    @Override
    public void loop(){
        controller.setPID(p, i, d);
        pos = hSlide.getCurrentPosition();
        double pid = controller.calculate(pos, target);
        double power = pid+f;

        intake.setPosition(.58);
        hSlide.setPower(power);

        telemetry.addData("pos", pos);
        telemetry.addData("target", target);
        telemetry.update();
    }
}