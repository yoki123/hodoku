/*
 * Copyright (C) 2008-12  Bernhard Hobiger
 *
 * This file is part of HoDoKu.
 *
 * HoDoKu is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * HoDoKu is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with HoDoKu. If not, see <http://www.gnu.org/licenses/>.
 */
package sudoku;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ResourceBundle;
import java.util.SortedMap;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author hobiwan
 */
@SuppressWarnings("serial")
public class CellZoomPanel extends javax.swing.JPanel {

	private static final int X_OFFSET = 10;
	private static final int Y_OFFSET = 33;
	private static final int SMALL_GAP = 6;
	private static final int LARGE_GAP = 14;
	private static final int COLOR_PANEL_MAX_HEIGHT = 50;
	private static final int DIFF_SIZE = 1;
	private static final String[] NUMBERS = new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9" };
	
	private MainFrame mainFrame;
	private Font buttonFont = null;
	private Font iconFont = null;
	private int buttonFontSize = -1;
	private int defaultButtonFontSize = -1;
	private int defaultButtonHeight = -1;
	private JButton[] setValueButtons = null;
	private JButton[] toggleCandidatesButtons = null;
	private JPanel[] cellPanels = null;
	private JPanel[] candidatePanels = null;
	private Color normButtonForeground = null;
	private Color normButtonBackground = null;
	private int aktColor = -1;
	private SudokuPanel sudokuPanel;
	private int colorImageHeight = -1;
	private Icon[] colorKuIcons = new Icon[9];
	private int colorButtonCount = 18;
	
	private javax.swing.JPanel candidateColorPanel;
	private javax.swing.JLabel cellColorLabel;
	private javax.swing.JPanel cellColorPanel;
	private javax.swing.JLabel chooseCandidateColorLabel;
	private javax.swing.JPanel chooseCandidateColorPanel;
	private javax.swing.JPanel chooseCellColorPanel;
	private javax.swing.JButton jFontButton;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JButton setValueButton1;
	private javax.swing.JButton setValueButton2;
	private javax.swing.JButton setValueButton3;
	private javax.swing.JButton setValueButton4;
	private javax.swing.JButton setValueButton5;
	private javax.swing.JButton setValueButton6;
	private javax.swing.JButton setValueButton7;
	private javax.swing.JButton setValueButton8;
	private javax.swing.JButton setValueButton9;
	private javax.swing.JLabel setValueLabel;
	private javax.swing.JPanel setValuePanel;
	private javax.swing.JLabel titleLabel;
	private javax.swing.JButton toggleCandidatesButton1;
	private javax.swing.JButton toggleCandidatesButton2;
	private javax.swing.JButton toggleCandidatesButton3;
	private javax.swing.JButton toggleCandidatesButton4;
	private javax.swing.JButton toggleCandidatesButton5;
	private javax.swing.JButton toggleCandidatesButton6;
	private javax.swing.JButton toggleCandidatesButton7;
	private javax.swing.JButton toggleCandidatesButton8;
	private javax.swing.JButton toggleCandidatesButton9;
	private javax.swing.JLabel toggleCandidatesLabel;
	private javax.swing.JPanel toggleCandidatesPanel;

	/**
	 * Creates new form CellZoomPanel
	 * 
	 * @param mainFrame
	 */
	public CellZoomPanel(MainFrame mainFrame) {
		
		this.mainFrame = mainFrame;
		
		cellPanels = new JPanel[colorButtonCount];
		candidatePanels = new JPanel[colorButtonCount];
		
		initComponents();

		setValueButtons = new JButton[] {
			setValueButton1, setValueButton2, setValueButton3, 
			setValueButton4, setValueButton5, setValueButton6, 
			setValueButton7, setValueButton8, setValueButton9 
		};
		
		toggleCandidatesButtons = new JButton[] { 
			toggleCandidatesButton1, toggleCandidatesButton2, toggleCandidatesButton3, 
			toggleCandidatesButton4, toggleCandidatesButton5, toggleCandidatesButton6,
			toggleCandidatesButton7, toggleCandidatesButton8, toggleCandidatesButton9 
		};
		
		normButtonForeground = setValueButton1.getForeground();
		normButtonBackground = setValueButton1.getBackground();

		jFontButton.setVisible(false);
		buttonFont = jFontButton.getFont();
		buttonFontSize = 11;
		defaultButtonFontSize = buttonFontSize;
		defaultButtonHeight = 23;
		iconFont = new Font(buttonFont.getName(), buttonFont.getStyle(), defaultButtonFontSize - DIFF_SIZE);

		int fontSize = 12;
		if (getFont().getSize() > 12) {
			fontSize = getFont().getSize();
		}
		
		Font font = titleLabel.getFont();
		titleLabel.setFont(new Font(font.getName(), Font.BOLD, fontSize));

		calculateLayout();
	}
	
	private JPanel createCellColorButtonPanel(int id) {
		
		JPanel panel = new StatusColorPanel(id);
		
		panel.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				chooseCellColor0PanelMouseClicked(evt);
			}
		});

		javax.swing.GroupLayout group = new javax.swing.GroupLayout(panel);
		panel.setLayout(group);
		group.setHorizontalGroup(group.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 18, Short.MAX_VALUE));
		group.setVerticalGroup(group.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 1, Short.MAX_VALUE));

		return panel;
	}
	
	private JPanel createCandidateColorButtonPanel(int id) {
		
		JPanel panel = new StatusColorPanel(id);
		panel.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				chooseCellColor0PanelMouseClicked(evt);
			}
		});

		javax.swing.GroupLayout group = new javax.swing.GroupLayout(panel);
		panel.setLayout(group);
		group.setHorizontalGroup(group.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 18, Short.MAX_VALUE));
		group.setVerticalGroup(group.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 21, Short.MAX_VALUE));

		return panel;
	}

	private void initComponents() {

		jPanel1 = new javax.swing.JPanel();
		titleLabel = new javax.swing.JLabel();
		setValueLabel = new javax.swing.JLabel();
		setValuePanel = new javax.swing.JPanel();
		setValueButton1 = new javax.swing.JButton();
		setValueButton2 = new javax.swing.JButton();
		setValueButton3 = new javax.swing.JButton();
		setValueButton4 = new javax.swing.JButton();
		setValueButton5 = new javax.swing.JButton();
		setValueButton6 = new javax.swing.JButton();
		setValueButton7 = new javax.swing.JButton();
		setValueButton8 = new javax.swing.JButton();
		setValueButton9 = new javax.swing.JButton();
		toggleCandidatesLabel = new javax.swing.JLabel();
		toggleCandidatesPanel = new javax.swing.JPanel();
		toggleCandidatesButton1 = new javax.swing.JButton();
		toggleCandidatesButton2 = new javax.swing.JButton();
		toggleCandidatesButton3 = new javax.swing.JButton();
		toggleCandidatesButton4 = new javax.swing.JButton();
		toggleCandidatesButton5 = new javax.swing.JButton();
		toggleCandidatesButton6 = new javax.swing.JButton();
		toggleCandidatesButton7 = new javax.swing.JButton();
		toggleCandidatesButton8 = new javax.swing.JButton();
		toggleCandidatesButton9 = new javax.swing.JButton();
		cellColorLabel = new javax.swing.JLabel();
		cellColorPanel = new javax.swing.JPanel();
		chooseCellColorPanel = new javax.swing.JPanel();
		chooseCandidateColorLabel = new javax.swing.JLabel();
		candidateColorPanel = new javax.swing.JPanel();
		chooseCandidateColorPanel = new javax.swing.JPanel();
		jFontButton = new javax.swing.JButton();

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 100, Short.MAX_VALUE));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 100, Short.MAX_VALUE));

		addComponentListener(new java.awt.event.ComponentAdapter() {
			public void componentResized(java.awt.event.ComponentEvent evt) {
				formComponentResized(evt);
			}
		});
		setLayout(null);

		titleLabel.setBackground(new java.awt.Color(0, 51, 255));
		titleLabel.setFont(new java.awt.Font("Tahoma", 1, 12));
		titleLabel.setForeground(new java.awt.Color(255, 255, 255));
		titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("intl/CellZoomPanel");
		titleLabel.setText(bundle.getString("CellZoomPanel.titleLabel.text"));
		titleLabel.setOpaque(true);
		add(titleLabel);
		titleLabel.setBounds(0, 0, 63, 15);

		setValueLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		setValueLabel.setText(bundle.getString("CellZoomPanel.setValueLabel.text"));
		add(setValueLabel);
		setValueLabel.setBounds(0, 0, 49, 14);

		setValuePanel.setLayout(new java.awt.GridLayout(3, 3));

		setValueButton1.setText("1");
		setValueButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				setValueButton1ActionPerformed(evt);
			}
		});
		setValuePanel.add(setValueButton1);

		setValueButton2.setText("2");
		setValueButton2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				setValueButton1ActionPerformed(evt);
			}
		});
		setValuePanel.add(setValueButton2);

		setValueButton3.setText("3");
		setValueButton3.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				setValueButton1ActionPerformed(evt);
			}
		});
		setValuePanel.add(setValueButton3);

		setValueButton4.setText("4");
		setValueButton4.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				setValueButton1ActionPerformed(evt);
			}
		});
		setValuePanel.add(setValueButton4);

		setValueButton5.setText("5");
		setValueButton5.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				setValueButton1ActionPerformed(evt);
			}
		});
		setValuePanel.add(setValueButton5);

		setValueButton6.setText("6");
		setValueButton6.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				setValueButton1ActionPerformed(evt);
			}
		});
		setValuePanel.add(setValueButton6);

		setValueButton7.setText("7");
		setValueButton7.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				setValueButton1ActionPerformed(evt);
			}
		});
		setValuePanel.add(setValueButton7);

		setValueButton8.setText("8");
		setValueButton8.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				setValueButton1ActionPerformed(evt);
			}
		});
		setValuePanel.add(setValueButton8);

		setValueButton9.setText("9");
		setValueButton9.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				setValueButton1ActionPerformed(evt);
			}
		});
		setValuePanel.add(setValueButton9);

		add(setValuePanel);
		setValuePanel.setBounds(0, 0, 117, 69);

		toggleCandidatesLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		toggleCandidatesLabel.setText(bundle.getString("CellZoomPanel.toggleCandidatesLabel.text"));
		add(toggleCandidatesLabel);
		toggleCandidatesLabel.setBounds(0, 0, 93, 14);

		toggleCandidatesPanel.setLayout(new java.awt.GridLayout(3, 3));

		toggleCandidatesButton1.setText("1");
		toggleCandidatesButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				toggleCandidatesButton1ActionPerformed(evt);
			}
		});
		toggleCandidatesPanel.add(toggleCandidatesButton1);

		toggleCandidatesButton2.setText("2");
		toggleCandidatesButton2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				toggleCandidatesButton1ActionPerformed(evt);
			}
		});
		toggleCandidatesPanel.add(toggleCandidatesButton2);

		toggleCandidatesButton3.setText("3");
		toggleCandidatesButton3.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				toggleCandidatesButton1ActionPerformed(evt);
			}
		});
		toggleCandidatesPanel.add(toggleCandidatesButton3);

		toggleCandidatesButton4.setText("4");
		toggleCandidatesButton4.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				toggleCandidatesButton1ActionPerformed(evt);
			}
		});
		toggleCandidatesPanel.add(toggleCandidatesButton4);

		toggleCandidatesButton5.setText("5");
		toggleCandidatesButton5.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				toggleCandidatesButton1ActionPerformed(evt);
			}
		});
		toggleCandidatesPanel.add(toggleCandidatesButton5);

		toggleCandidatesButton6.setText("6");
		toggleCandidatesButton6.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				toggleCandidatesButton1ActionPerformed(evt);
			}
		});
		toggleCandidatesPanel.add(toggleCandidatesButton6);

		toggleCandidatesButton7.setText("7");
		toggleCandidatesButton7.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				toggleCandidatesButton1ActionPerformed(evt);
			}
		});
		toggleCandidatesPanel.add(toggleCandidatesButton7);

		toggleCandidatesButton8.setText("8");
		toggleCandidatesButton8.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				toggleCandidatesButton1ActionPerformed(evt);
			}
		});
		toggleCandidatesPanel.add(toggleCandidatesButton8);

		toggleCandidatesButton9.setText("9");
		toggleCandidatesButton9.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				toggleCandidatesButton1ActionPerformed(evt);
			}
		});
		toggleCandidatesPanel.add(toggleCandidatesButton9);

		add(toggleCandidatesPanel);
		toggleCandidatesPanel.setBounds(0, 0, 117, 69);

		cellColorLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		cellColorLabel.setText(bundle.getString("CellZoomPanel.colorCellsLabel.text"));
		add(cellColorLabel);
		cellColorLabel.setBounds(0, 0, 105, 14);

		cellColorPanel.setBackground(new java.awt.Color(255, 255, 255));
		cellColorPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

		javax.swing.GroupLayout cellColorPanelLayout = new javax.swing.GroupLayout(cellColorPanel);
		cellColorPanel.setLayout(cellColorPanelLayout);
		cellColorPanelLayout.setHorizontalGroup(cellColorPanelLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 41, Short.MAX_VALUE));
		cellColorPanelLayout.setVerticalGroup(cellColorPanelLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE));

		add(cellColorPanel);
		cellColorPanel.setBounds(0, 0, 45, 4);

		chooseCellColorPanel.setLayout(new java.awt.GridLayout(2, 9, 1, 1));
		
		{			
			for (int i = 0; i < colorButtonCount; i++) {
				cellPanels[i] = createCellColorButtonPanel(-2 + i);
			}
			
			for (int i = 2; i < colorButtonCount; i += 2) {
				chooseCellColorPanel.add(cellPanels[i]);
			}
			
			chooseCellColorPanel.add(cellPanels[0]);
			
			for (int i = 3; i < colorButtonCount; i += 2) {
				chooseCellColorPanel.add(cellPanels[i]);
			}
			
			chooseCellColorPanel.add(cellPanels[1]);
		}

		add(chooseCellColorPanel);
		chooseCellColorPanel.setBounds(0, 0, 113, 3);

		chooseCandidateColorLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		chooseCandidateColorLabel.setText(bundle.getString("CellZoomPanel.chooseCandidateColorLabel.text"));
		add(chooseCandidateColorLabel);
		chooseCandidateColorLabel.setBounds(0, 0, 142, 14);

		candidateColorPanel.setBackground(new java.awt.Color(255, 255, 255));
		candidateColorPanel
				.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

		javax.swing.GroupLayout candidateColorPanelLayout = new javax.swing.GroupLayout(candidateColorPanel);
		candidateColorPanel.setLayout(candidateColorPanelLayout);
		candidateColorPanelLayout.setHorizontalGroup(candidateColorPanelLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 41, Short.MAX_VALUE));
		candidateColorPanelLayout.setVerticalGroup(candidateColorPanelLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 40, Short.MAX_VALUE));

		add(candidateColorPanel);
		candidateColorPanel.setBounds(0, 0, 45, 44);

		chooseCandidateColorPanel.setLayout(new java.awt.GridLayout(2, 9, 1, 1));

		{
			for (int i = 0; i < colorButtonCount; i++) {
				candidatePanels[i] = createCandidateColorButtonPanel(-2 + i);
			}
			
			for (int i = 2; i < colorButtonCount; i += 2) {
				chooseCandidateColorPanel.add(candidatePanels[i]);
			}
			
			chooseCandidateColorPanel.add(candidatePanels[0]);
			
			for (int i = 3; i < colorButtonCount; i += 2) {
				chooseCandidateColorPanel.add(candidatePanels[i]);
			}
			
			chooseCandidateColorPanel.add(candidatePanels[1]);
		}

		add(chooseCandidateColorPanel);
		chooseCandidateColorPanel.setBounds(0, 0, 113, 43);

		jFontButton.setText("FontButton");
		jFontButton.setEnabled(false);
		add(jFontButton);
		jFontButton.setBounds(29, 130, 110, 23);
	}

	private void formComponentResized(java.awt.event.ComponentEvent evt) {
		calculateLayout();
		printSize();
	}

	private void setValueButton1ActionPerformed(java.awt.event.ActionEvent evt) {
		setValue((JButton) evt.getSource());
	}

	private void toggleCandidatesButton1ActionPerformed(java.awt.event.ActionEvent evt) {
		handleCandidateChange((JButton) evt.getSource());
	}

	private void chooseCellColor0PanelMouseClicked(java.awt.event.MouseEvent evt) {
		handleColorChange((JPanel) evt.getSource(), evt.isControlDown());
	}

	private void handleCandidateChange(JButton button) {
		
		int candidate = -1;
		
		if (sudokuPanel.getActiveRow() == -1 || sudokuPanel.getActiveCol() == -1) {
			return;
		}
		
		for (int i = 0; i < toggleCandidatesButtons.length; i++) {
			if (button == toggleCandidatesButtons[i]) {
				candidate = i + 1;
				break;
			}
		}
		
		if (sudokuPanel != null && candidate != -1) {
			if (aktColor == -1) {
				sudokuPanel.toggleOrRemoveCandidateFromCellZoomPanel(candidate);
			} else {
				sudokuPanel.handleColoring(candidate);
			}
		}
	}

	private void setValue(JButton button) {
		
		int number = -1;
		
		if (sudokuPanel.getActiveRow() == -1 || sudokuPanel.getActiveCol() == -1) {
			return;
		}
		
		for (int i = 0; i < setValueButtons.length; i++) {
			if (button == setValueButtons[i]) {
				number = i + 1;
				break;
			}
		}
		
		if (sudokuPanel != null && number != -1) {
			sudokuPanel.setCellFromCellZoomPanel(number);
		}
	}

	private void handleColorChange(JPanel panel, boolean isCtrlDown) {
		
		boolean found = false;
		boolean isCell = false;
		int colorNumber = -1;
		
		for (int i = 0; i < cellPanels.length; i++) {
			if (panel == cellPanels[i]) {
				colorNumber = i - 2; // adjust for -1 and -2
				isCell = true;
				found = true;
				break;
			}
		}
		
		if (!found) {
			for (int i = 0; i < candidatePanels.length; i++) {
				if (panel == candidatePanels[i]) {
					colorNumber = i - 2; // adjust for -1 and -2
					isCell = false;
					found = true;
					break;
				}
			}
		}
		
		if (found && mainFrame != null) {
			
			if (isCtrlDown) {
				if (isCell) {
					sudokuPanel.clearCellColor(colorNumber);
				} else {
					sudokuPanel.clearCandidateColor(colorNumber);
				}
			} else {
				mainFrame.setColoring(colorNumber, isCell);	
			}
		}
	}

	public final void calculateLayout() {
		
		if (defaultButtonHeight == -1) {
			// not yet initialized!
			return;
		}
		
		int width = getWidth();
		int height = getHeight();
		int y = Y_OFFSET;

		// adjust height and width for the labels
		FontMetrics metrics = getFontMetrics(getFont());
		int textHeight = metrics.getHeight();

		// calculate widths and height of components
		// how much vertical space is actually available?
		int labelHeight = 4 * textHeight;
		int availableVert = height - Y_OFFSET - 4 * (SMALL_GAP + LARGE_GAP) - labelHeight;

		// try default sizes
		int buttonPanelHeight = availableVert * 2 / 6;
		int colorPanelHeight = availableVert / 6;
		if (colorPanelHeight > COLOR_PANEL_MAX_HEIGHT) {
			colorPanelHeight = COLOR_PANEL_MAX_HEIGHT;
		}
		
		if (buttonPanelHeight > (width - 2 * X_OFFSET)) {
			buttonPanelHeight = width - 2 * X_OFFSET;
		}
		
		// adjust color panels
		if (buttonPanelHeight < 120) {
			colorPanelHeight -= (120 - buttonPanelHeight);
			buttonPanelHeight = 120;
		}
		
		int colorPanelGesWidth = colorPanelHeight * 6;
		if (colorPanelGesWidth > width - 2 * X_OFFSET) {
			colorPanelHeight = (int) ((width - 2 * X_OFFSET) / 4.5);
		}
		
		colorPanelGesWidth = colorPanelHeight * 6;
		int newColorImageHeight = colorPanelHeight * 2 / 3;

		// ok, do the layout
		titleLabel.setSize(width, textHeight);
		setValueLabel.setSize(width - 2 * X_OFFSET, textHeight);
		setValueLabel.setLocation(X_OFFSET, y);
		y += textHeight;
		y += SMALL_GAP;
		setValuePanel.setSize(buttonPanelHeight, buttonPanelHeight);
		setValuePanel.setLocation((width - buttonPanelHeight) / 2, y);
		setValuePanel.doLayout();
		y += buttonPanelHeight;
		y += LARGE_GAP;
		toggleCandidatesLabel.setSize(width - 2 * X_OFFSET, textHeight);
		toggleCandidatesLabel.setLocation(X_OFFSET, y);
		y += textHeight;
		y += SMALL_GAP;
		toggleCandidatesPanel.setSize(buttonPanelHeight, buttonPanelHeight);
		toggleCandidatesPanel.setLocation((width - buttonPanelHeight) / 2, y);
		toggleCandidatesPanel.doLayout();

		int cpx = (width - colorPanelGesWidth) / 2;
		y = height - 2 * (SMALL_GAP + LARGE_GAP) - textHeight - textHeight - 2 * colorPanelHeight;
		cellColorLabel.setSize(width - 2 * X_OFFSET, textHeight);
		cellColorLabel.setLocation(X_OFFSET, y);
		y += textHeight;
		y += SMALL_GAP;
		cellColorPanel.setSize(colorPanelHeight * 2 / 3, colorPanelHeight * 2 / 3);
		cellColorPanel.setLocation(cpx, y + colorPanelHeight / 6);
		cellColorPanel.doLayout();
		chooseCellColorPanel.setSize((5 * colorPanelHeight), colorPanelHeight);
		chooseCellColorPanel.setLocation(cpx + colorPanelHeight, y);
		chooseCellColorPanel.doLayout();
		y += colorPanelHeight;
		y += LARGE_GAP;
		chooseCandidateColorLabel.setSize(width - 2 * X_OFFSET, textHeight);
		chooseCandidateColorLabel.setLocation(X_OFFSET, y);
		y += textHeight;
		y += SMALL_GAP;
		candidateColorPanel.setSize(colorPanelHeight * 2 / 3, colorPanelHeight * 2 / 3);
		candidateColorPanel.setLocation(cpx, y + colorPanelHeight / 6);
		candidateColorPanel.doLayout();
		chooseCandidateColorPanel.setSize((5 * colorPanelHeight), colorPanelHeight);
		chooseCandidateColorPanel.setLocation(cpx + colorPanelHeight, y);
		chooseCandidateColorPanel.doLayout();

		// set correct font size for buttons
		int newFontSize = defaultButtonFontSize * buttonPanelHeight / (defaultButtonHeight * 4);
		if (newFontSize > 0 && newFontSize != buttonFontSize) {

			buttonFontSize = newFontSize;
			buttonFont = new Font(buttonFont.getName(), buttonFont.getStyle(), buttonFontSize);
			iconFont = new Font(buttonFont.getName(), buttonFont.getStyle(), buttonFontSize - DIFF_SIZE);

			for (int i = 0; i < setValueButtons.length; i++) {
				setValueButtons[i].setFont(buttonFont);
				toggleCandidatesButtons[i].setFont(buttonFont);
			}
		}
		
		// ColorKu icons should be the same size as the candidate numbers
		// icons are only created, if colorKu mode is active
		if (newColorImageHeight > 0 && 
			Options.getInstance().isShowColorKuAct() && 
			newColorImageHeight != colorImageHeight) {
			
			colorImageHeight = newColorImageHeight;
			for (int i = 0; i < colorKuIcons.length; i++) {
				colorKuIcons[i] = new ImageIcon(new ColorKuImage(colorImageHeight, Options.getInstance().getColorKuColor(i + 1)));
			}
		}
		
		repaint();
	}

	/**
	 * Two modi: normal operation or coloring.<br>
	 * Normal operation:
	 * <ul>
	 * <li>activeColor has to be -1</li>
	 * <li>values and candidates contain valid choices (set of cells or single
	 * cell)</li>
	 * </ul>
	 * Coloring:
	 * <ul>
	 * <li>activeColor holds the color, colorCellOrCandidate is true for coloring
	 * cells</li>
	 * <li>buttons are only available, if a single cell is selected
	 * <li>
	 * <li>if a set of cells is selected, coloredCells and coloredCandidates are
	 * null</li>
	 * </ul>
	 * 
	 * @param values
	 * @param candidates
	 * @param aktColor
	 * @param index
	 * @param colorCellOrCandidate
	 * @param singleCell
	 * @param coloredCells
	 * @param coloredCandidates
	 */
	public void update(
			SudokuSet values, 
			SudokuSet candidates, 
			int aktColor, 
			int index, 
			boolean colorCellOrCandidate,
			boolean singleCell, 
			SortedMap<Integer, Integer> coloredCells,
			SortedMap<Integer, Integer> coloredCandidates) {
		
		// reset all buttons
		for (int i = 0; i < setValueButtons.length; i++) {
			setValueButtons[i].setText("");
			setValueButtons[i].setEnabled(false);
			setValueButtons[i].setForeground(normButtonForeground);
			setValueButtons[i].setBackground(normButtonBackground);
			setValueButtons[i].setIcon(null);
			toggleCandidatesButtons[i].setText("");
			toggleCandidatesButtons[i].setEnabled(false);
			toggleCandidatesButtons[i].setForeground(normButtonForeground);
			toggleCandidatesButtons[i].setBackground(normButtonBackground);
			toggleCandidatesButtons[i].setIcon(null);
		}
		
		cellColorPanel.setBackground(Options.getInstance().getDefaultCellColor());
		candidateColorPanel.setBackground(Options.getInstance().getDefaultCellColor());

		// now set accordingly
		this.aktColor = aktColor;
		if (aktColor == -1) {
			// no coloring -> buttons are available
			for (int i = 0; i < values.size(); i++) {
				int cand = values.get(i) - 1;
				if (cand >= 0 && cand <= 8) {
					if (Options.getInstance().isShowColorKuAct()) {
						setValueButtons[cand].setText(null);
						setValueButtons[cand].setIcon(colorKuIcons[cand]);
					} else {
						setValueButtons[cand].setText(NUMBERS[cand]);
						setValueButtons[cand].setIcon(null);
					}
					setValueButtons[cand].setEnabled(true);
				}
			}
			
			for (int i = 0; i < candidates.size(); i++) {
				int cand = candidates.get(i) - 1;
				if (cand >= 0 && cand <= 8) {
					if (Options.getInstance().isShowColorKuAct()) {
						toggleCandidatesButtons[cand].setText(null);
						toggleCandidatesButtons[cand].setIcon(colorKuIcons[cand]);
					} else {
						toggleCandidatesButtons[cand].setText(NUMBERS[cand]);
						toggleCandidatesButtons[cand].setIcon(null);
					}
					toggleCandidatesButtons[cand].setEnabled(true);
				}
			}
			
			if (singleCell) {
				toggleCandidatesLabel.setText(ResourceBundle.getBundle("intl/CellZoomPanel")
						.getString("CellZoomPanel.toggleCandidatesLabel.text"));
				for (int i = 0; i < toggleCandidatesButtons.length; i++) {
					toggleCandidatesButtons[i].setEnabled(true);
				}
			} else {
				toggleCandidatesLabel.setText(ResourceBundle.getBundle("intl/CellZoomPanel")
						.getString("CellZoomPanel.toggleCandidatesLabel.text2"));
			}
			
		} else {
			
			// coloring
			if (colorCellOrCandidate) {
				cellColorPanel.setBackground(Options.getInstance().getColoringColors()[aktColor]);
			} else {
				candidateColorPanel.setBackground(Options.getInstance().getColoringColors()[aktColor]);
			}
			
			if (coloredCells != null) {

				if (colorCellOrCandidate == false) {
					for (int i = 0; i < candidates.size(); i++) {
						int cand = candidates.get(i);
						if (coloredCandidates.containsKey(index * 10 + cand)) {
							int candIndex = coloredCandidates.get(index * 10 + cand);
							Color candColor = Options.getInstance().getColoringColors()[candIndex];
							toggleCandidatesButtons[cand - 1].setForeground(candColor);
							toggleCandidatesButtons[cand - 1].setBackground(candColor);
							toggleCandidatesButtons[cand - 1].setIcon(createImage(colorImageHeight, candIndex, cand));
							toggleCandidatesButtons[cand - 1].setEnabled(true);
						} else {
							toggleCandidatesButtons[cand - 1].setText(NUMBERS[cand - 1]);
							toggleCandidatesButtons[cand - 1].setEnabled(true);
						}
					}
				}
			}
		}

	}

	private ImageIcon createImage(int size, int colorIndex, int cand) {
		
		if (size > 0) {
			
			Image img = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = (Graphics2D) img.getGraphics();
			Color color = Options.getInstance().getDefaultCellColor();
			if (colorIndex < Options.getInstance().getColoringColors().length) {
				color = Options.getInstance().getColoringColors()[colorIndex];
			}
			
			g.setColor(color);
			g.fillRect(0, 0, size, size);
			if (cand > 0) {
				if (Options.getInstance().isShowColorKuAct()) {
					BufferedImage cImg = new ColorKuImage(size, Options.getInstance().getColorKuColor(cand));
					g.drawImage(cImg, 0, 0, null);
				} else {
					g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
					g.setFont(iconFont);
					FontMetrics fm = g.getFontMetrics();
					String str = String.valueOf(cand);
					int strWidth = fm.stringWidth(str);
					int strHeight = fm.getAscent();
					g.setColor(normButtonForeground);
					g.drawString(String.valueOf(cand), (size - strWidth) / 2, (size + strHeight - 2) / 2);
				}
			}
			
			return new ImageIcon(img);
		} else {
			return null;
		}
	}

	private void printSize() {}

	public void setTitleLabelColors(Color fore, Color back) {
		titleLabel.setBackground(back);
		titleLabel.setForeground(fore);
	}

	/**
	 * @param sudokuPanel the sudokuPanel to set
	 */
	public void setSudokuPanel(SudokuPanel sudokuPanel) {
		this.sudokuPanel = sudokuPanel;
	}
}
