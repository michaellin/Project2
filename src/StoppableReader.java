package src;
/**
 * Copyright (C) 2002 Michael Green <mtgreen@cs.ucsd.edu>
 * 
 * Copyright (C) 2002 Paul Kube <kube@cs.ucsd.edu>
 * 
 * Copyright (C) 2005 Owen Astrachan <ola@cs.duke.edu>
 * 
 * Copyright (C) 2011 Hoa Long Tam <hoalong.tam@berkeley.edu> and Armin Samii
 * <samii@berkeley.edu>
 * 
 * This file is part of CS Boggle.
 * 
 * CS Boggle is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * CS Boggle is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * CS boggle. If not, see <http://www.gnu.org/licenses/>.
 */

import java.io.InputStream;
import java.util.Scanner;

import javax.swing.JOptionPane;
import javax.swing.ProgressMonitor;
import javax.swing.ProgressMonitorInputStream;

public class StoppableReader {
  public static ProgressMonitorInputStream getMonitorableStream(
    InputStream stream, String message) {

    if (stream == null) {
      System.out.println("whoops");
    }
    final ProgressMonitorInputStream pmis =
      new ProgressMonitorInputStream(null, message, stream);

    ProgressMonitor progress = pmis.getProgressMonitor();
    progress.setMillisToDecideToPopup(1);
    progress.setMillisToPopup(1);

    return pmis;
  }

  public static void doProcess(final ProgressMonitorInputStream pmis,
    final LexiconInterface lex, String message) {
    final ProgressMonitor progress = pmis.getProgressMonitor();
    Thread fileReaderThread = new Thread() {
      @Override
      public void run() {
        lex.load(new Scanner(pmis, "UTF-8"));
        if (progress.isCanceled()) {
          showError("reading cancelled");
        }
      }
    };
    fileReaderThread.start();
  }

  public static void showError(String message) {
    JOptionPane.showMessageDialog(null, message, "Boggle Error",
                                  JOptionPane.ERROR_MESSAGE);
  }
}
