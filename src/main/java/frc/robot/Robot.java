// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.simulation.BatterySim;
import edu.wpi.first.wpilibj.simulation.RoboRioSim;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

/**
 * This is a sample program to demonstrate the use of state-space classes in robot simulation. This
 * robot has a flywheel, elevator, arm and differential drivetrain, and interfaces with the sim
 * GUI's {@link edu.wpi.first.wpilibj.simulation.Field2d} class.
 */
public class Robot extends TimedRobot {
  private RobotContainer m_robotContainer;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    m_robotContainer = new RobotContainer();
    m_chooser.setDefaultOption("Auton1", kDefaultAuto);
    m_chooser.addOption("Auton2", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
  }
  private static final String kDefaultAuto = "Auton1";
  private static final String kCustomAuto = "Auton2";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  private Command m_autonomousCommand;
  
  @Override
  public void simulationPeriodic() {
    // Here we calculate the battery voltage based on drawn current.
    // As our robot draws more power from the battery its voltage drops.
    // The estimated voltage is highly dependent on the battery's internal
    // resistance.
    double drawCurrent = m_robotContainer.getRobotDrive().getDrawnCurrentAmps();
    double loadedVoltage = BatterySim.calculateDefaultBatteryLoadedVoltage(drawCurrent);
    RoboRioSim.setVInVoltage(loadedVoltage);
  }

  @Override
  public void robotPeriodic() {
    
    CommandScheduler.getInstance().run();
  }

  @Override
  public void autonomousInit() {
    Command m_autonomousCommand = m_robotContainer.getAutonCommand1();

    // shcedule the auton command
    if(m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
    Command m_autonomousCommand2 = m_robotContainer.getAutonomousCommand();

    // shcedule the auton commandgi
    if(m_autonomousCommand2 != null) {
      m_autonomousCommand2.schedule();
    }
    //m_robotContainer.getAutonomousCommand().schedule();
    m_autoSelected = m_chooser.getSelected();
    System.out.println("Auto selected: " + m_autoSelected);
  }
  



  public void autonomousPeriodic() {
    
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }
@Override
public void teleopInit() {
  //this makes sure that the auton stops running
  //when teleop starts. if you want it to continue until interupted
  //by another command, remove this line or comment it out
  if (m_autonomousCommand != null) {
    m_autonomousCommand.cancel();
  }
}
  @Override
  public void disabledInit() {
    CommandScheduler.getInstance().cancelAll();
    m_robotContainer.zeroAllOutputs();
  }
}
