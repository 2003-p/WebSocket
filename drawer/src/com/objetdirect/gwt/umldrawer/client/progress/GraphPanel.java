package com.objetdirect.gwt.umldrawer.client.progress;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.Selection;
import com.google.gwt.visualization.client.events.SelectHandler;
import com.google.gwt.visualization.client.visualizations.corechart.LineChart;
import com.google.gwt.visualization.client.visualizations.corechart.Options;
import com.google.gwt.visualization.client.visualizations.corechart.TextStyle;


public class GraphPanel extends AbsolutePanel{
	private Panel parent;
	private DataTable dataTable;
	private String studentId;

//	public GraphTestPanel(DataTable data){
//		// Create a pie chart visualization.
//		LineChart lineChart = new LineChart(data, createOptions());
//		//VisualizationUtils.loadVisualizationApi(onLoadCallback, PieChart.PACKAGE);
//		lineChart.addSelectHandler(createSelectHandler(lineChart));
//		this.add(lineChart);
//	}

	public GraphPanel(DataTable data, String studentId, Panel parent){
		// Create a pie chart visualization.
		this.parent = parent;
		this.dataTable = data;
		this.studentId = studentId;
		LineChart lineChart = new LineChart(data, createOptions(studentId));
		//VisualizationUtils.loadVisualizationApi(onLoadCallback, PieChart.PACKAGE);
		lineChart.addSelectHandler(createSelectHandler(lineChart));
		this.add(lineChart);
	}

	public  Options createOptions(String studentId) {
		Options options = Options.create();
		options.setWidth(800);
		options.setHeight(400);
		options.setTitle(studentId);
		TextStyle style = TextStyle.create();
		style.setFontSize(40);
		options.setTitleTextStyle(style);
		return options;
	}

	private SelectHandler createSelectHandler(final LineChart lineChart) {
		return new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				String message = "";

				// May be multiple selections.
				JsArray<Selection> selections = lineChart.getSelections();
				Selection selection = selections.get(0);
				if (selection.isCell()) {
					// isCell() returns true if a cell has been selected.

					// getRow() returns the row number of the selected cell.
					int row = selection.getRow();
					// getColumn() returns the column number of the selected cell.
					int column = selection.getColumn();

					dataTable.removeRows(0, row);
//					clear();
//					LineChart lineChart = new LineChart(dataTable, GraphPanel.this.createOptions( GraphPanel.this.studentId ) );
//					//VisualizationUtils.loadVisualizationApi(onLoadCallback, PieChart.PACKAGE);
//					lineChart.addSelectHandler(createSelectHandler(lineChart));
//					add(lineChart);
				}

//				for (int i = 0; i < selections.length(); i++) {
//					// add a new line for each selection
//					message += i == 0 ? "" : "\n";
//
//					Selection selection = selections.get(i);
//
//					if (selection.isCell()) {
//						// isCell() returns true if a cell has been selected.
//
//						// getRow() returns the row number of the selected cell.
//						int row = selection.getRow();
//						// getColumn() returns the column number of the selected cell.
//						int column = selection.getColumn();
//						message += "cell " + row + ":" + column + " selected";
//					} else if (selection.isRow()) {
//						// isRow() returns true if an entire row has been selected.
//
//						// getRow() returns the row number of the selected row.
//						int row = selection.getRow();
//						message += "row " + row + " selected";
//					} else {
//						// unreachable
//						message += "Line chart selections should be either row selections or cell selections.";
//						message += "  Other visualizations support column selections as well.";
//					}
//				}

			}
		};
	}

}
