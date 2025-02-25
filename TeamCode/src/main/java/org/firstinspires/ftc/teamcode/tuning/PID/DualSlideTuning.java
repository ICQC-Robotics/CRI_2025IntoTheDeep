package org.firstinspires.ftc.teamcode.tuning.PID;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import org.firstinspires.ftc.teamcode.Const;

@Config
@TeleOp
public class DualSlideTuning extends OpMode {

    public static double p = 0, i = 0, d = 0.0;
    public static double f = 0;
    public static double target = 500;

    private PIDController controller;
    private int pos1, pos2;
    private DcMotorEx motor1, motor2;

    @Override
    public void init() {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        motor1 = hardwareMap.get(DcMotorEx.class, Const.lSlide);
        motor2 = hardwareMap.get(DcMotorEx.class, Const.rSlide);

        motor1.setDirection(DcMotorSimple.Direction.FORWARD);
        motor2.setDirection(DcMotorSimple.Direction.REVERSE);

        motor1.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        motor1.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        motor2.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        motor2.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        controller = new PIDController(p, i, d);
    }

    @Override
    public void loop() {
        controller.setPID(p, i, d);
        pos1 = motor1.getCurrentPosition();
        double pid = controller.calculate(pos1, target);
        double power = pid + f;

        motor1.setPower(power);
        motor2.setPower(power);

        telemetry.addData("pos1", pos1);
        telemetry.addData("pos2", pos2);
        telemetry.addData("target", target);
        telemetry.addData("power", power);
        telemetry.update();
    }
}