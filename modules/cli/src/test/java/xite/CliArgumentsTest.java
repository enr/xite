package xite;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.beust.jcommander.JCommander;
import com.github.enr.xite.BuildCommandArgs;
import com.github.enr.xite.CleanCommandArgs;
import com.github.enr.xite.DeployCommandArgs;
import com.github.enr.xite.MainCommandArgs;
import com.github.enr.xite.RunCommandArgs;

@Test(suiteName="Command Line Arguments")
public class CliArgumentsTest {
	
	JCommander jc;
	MainCommandArgs mainArgs;
	BuildCommandArgs buildArgs;
	RunCommandArgs runArgs;
	DeployCommandArgs deployArgs;
	CleanCommandArgs cleanArgs;

	@BeforeMethod
	public void setupJcommander() {
		mainArgs = new MainCommandArgs();
		buildArgs = new BuildCommandArgs();
		runArgs = new RunCommandArgs();
		deployArgs = new DeployCommandArgs();
		cleanArgs = new CleanCommandArgs();
		jc = new JCommander(mainArgs);
		jc.addCommand("build", buildArgs);
		jc.addCommand("run", runArgs);
		jc.addCommand("deploy", deployArgs);
		jc.addCommand("clean", cleanArgs);
	}
	
	@Test(description="No arguments parsing")
	public void noArgs() {
		jc.parse(new String[0]);
		assertFalse(mainArgs.isInfo());
		assertFalse(mainArgs.isHelp());
	}
	
	@Test(description="'help' argument parsing")
	public void helpArgs() {
		jc.parse("-h");
		assertTrue(mainArgs.isHelp());
	}

	@Test(description="Arguments parsing for command 'build'")
	public void defaultBuildArgs() {
		jc.parse("build");
		assertFalse(mainArgs.isInfo());
		assertFalse(mainArgs.isHelp());
		assertEquals(jc.getParsedCommand(), "build");
		assertEquals(buildArgs.getSource(), "./src/xite");
		assertEquals(buildArgs.getDestination(), "./target/xite");
	}

	@Test(description="Arguments parsing for command 'build'")
	public void buildArgs() {
		jc.parse("build", "-s", "sou/rce", "--destination", "../../dest/ination");
		assertFalse(mainArgs.isInfo());
		assertFalse(mainArgs.isHelp());
		assertEquals(jc.getParsedCommand(), "build");
		assertEquals(buildArgs.getSource(), "sou/rce");
		assertEquals(buildArgs.getDestination(), "../../dest/ination");
	}
	
	@Test(description="Arguments parsing for command 'run'")
	public void defaultRunArgs() {
		jc.parse("run");
		assertFalse(mainArgs.isInfo());
		assertFalse(mainArgs.isHelp());
		assertEquals(jc.getParsedCommand(), "run");
		assertEquals(runArgs.getSource(), "./src/xite");
		assertEquals(runArgs.getDestination(), "./target/xite");
		assertEquals(runArgs.getPort().intValue(), 8080);
	}
	
	@Test(description="Arguments parsing for command 'run'")
	public void runArgs() {
		jc.parse("run", "--source", "../s/o/urce", "-d", "d", "-p", "9999");
		assertFalse(mainArgs.isInfo());
		assertFalse(mainArgs.isHelp());
		assertEquals(jc.getParsedCommand(), "run");
		assertEquals(runArgs.getSource(), "../s/o/urce");
		assertEquals(runArgs.getDestination(), "d");
		assertEquals(runArgs.getPort().intValue(), 9999);


	}
	/*
	@Test(description="Arguments parsing for command 'list'")
	public void listArgs() {
		jc.parse("list");
		assertFalse(cm.isInfo());
		assertFalse(cm.isHelp());
		assertEquals(jc.getParsedCommand(), "list");
	}
	*/
}