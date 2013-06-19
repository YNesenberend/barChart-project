package com.sandbox.charts;



import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;

public abstract class Sandbox {
	protected Display display;
	private IWorkbench workbench;
	//protected final ObjectRuntime runtime = new ObjectRuntime();

	public void run()  {
		display = new Display ();
		final Shell shell = new Shell (display, SWT.SHELL_TRIM);
		try {
			//setup();
			createWindows(shell);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println(ex.getMessage());
			shell.close();
		}
		if (!shell.isDisposed())
			shell.open ();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch ()) display.sleep ();
		}
		display.dispose ();
	}
	
	protected void setup() throws Exception {
		throw new Exception("Missing Code");
		//runtime.addServices(com.vastech.eclipse.ServiceRegistrar.createServices(AppContext.PLUGIN_ID, display));
	}

	final protected IWorkbench workbench() throws Exception {
		if (workbench == null) {
			throw new Exception("Missing Code");
			//workbench = new SandboxWorkbench(display,runtime);
		}
		return workbench;
	}

	protected static Object createSession() throws Exception {
		throw new Exception("Missing Code");
//		CuffSession result = new CuffSession("localhost", "admin", "admin");
//		SecuritySubsystem ss = new SecuritySubsystem(result);
//		ss.register();
//		return result;
	}

	protected abstract void createWindows(Shell shell) throws Exception;
}
