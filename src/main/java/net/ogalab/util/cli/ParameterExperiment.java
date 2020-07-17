package net.ogalab.util.cli;


public class ParameterExperiment {
	
	Parameter param = new Parameter();
	
	public static void main(String[] args) {
		ParameterExperiment obj = new ParameterExperiment();
		String[] args2 = obj.setParameters(args, "ParameterExperiment");
		
		for (String a : args2) {
			System.out.println(a);
		}
	}
	
	private String[] setParameters(String[] args, String progName) {
		
		param.setDefault("Generation", 10000);
		param.setDefault("Interval", 1000);

		String[] unrecognizedArgs = null;
	    // Set parameters on the object.
		param.setDefault("ProgramName", progName);
		unrecognizedArgs = param.parseCommandLine(args, progName);
		param.printValidParameters();

		return unrecognizedArgs;
		
	}

}
