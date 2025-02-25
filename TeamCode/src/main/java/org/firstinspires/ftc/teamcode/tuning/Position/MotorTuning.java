package org.firstinspires.ftc.teamcode.tuning.Position;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import org.firstinspires.ftc.teamcode.Const;

@Config
@TeleOp
public class MotorTuning extends OpMode {
    private DcMotorEx motor1, motor2;

    public static double p = 0.0;
    public static double i = 0.0;
    public static double d = 0.0;
    public static double f = 0.0;

    public static int targetTicks = 0;

    private double motorPower = 0.0;

    @Override
    public void init() {
        motor1 = hardwareMap.get(DcMotorEx.class, Const.rSlide);
        motor2 = hardwareMap.get(DcMotorEx.class, Const.lSlide);

        motor1.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        motor2.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        PIDFCoefficients pidfCoefficients = new PIDFCoefficients(p, i, d, f);
        motor1.setPIDFCoefficients(DcMotorEx.RunMode.RUN_USING_ENCODER, pidfCoefficients);
        motor2.setPIDFCoefficients(DcMotorEx.RunMode.RUN_USING_ENCODER, pidfCoefficients);

        motor1.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        motor2.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

        motor1.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        motor2.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
    }

    @Override
    public void loop() {
        PIDFCoefficients pidfCoefficients = new PIDFCoefficients(p, i, d, f);
        motor1.setPIDFCoefficients(DcMotorEx.RunMode.RUN_TO_POSITION, pidfCoefficients);
        motor2.setPIDFCoefficients(DcMotorEx.RunMode.RUN_TO_POSITION, pidfCoefficients);

        motor1.setTargetPosition(targetTicks);
        motor2.setTargetPosition(targetTicks);

        motor1.setPower(motorPower);
        motor2.setPower(motorPower);

        if (gamepad1.dpad_up) {
            motorPower += 0.01;
        } else if (gamepad1.dpad_down) {
            motorPower -= 0.01;
        }

        motorPower = Math.max(0.0, Math.min(1.0, motorPower));

        telemetry.addData("Motor Power", motorPower);
        telemetry.addData("Target Ticks", targetTicks);
        telemetry.addData("Motor 1 Current Position", motor1.getCurrentPosition());
        telemetry.addData("Motor 2 Current Position", motor2.getCurrentPosition());
        telemetry.addData("Motor 1 Target Position", motor1.getTargetPosition());
        telemetry.addData("Motor 2 Target Position", motor2.getTargetPosition());
        telemetry.addData("PIDF Coefficients", "P: %.2f, I: %.2f, D: %.2f, F: %.2f", p, i, d, f);
        telemetry.update();
    }
}