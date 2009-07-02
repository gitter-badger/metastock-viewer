package com.gruszecm.mstock.browser;

import java.awt.Component;
import java.util.Date;

import javax.swing.JDesktopPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;

import com.mac.verec.models.Instrument;
import com.mac.verec.models.NumberDate;



public class DataFrame extends AbstractBrowserFrame {
	private static final long serialVersionUID = 463603545282068111L;
	private Instrument instrument;
	private JTable table;
	private static RowFilter<DataTableModel, Integer> currentFilter = DataFilter.NONE;
	
	public enum FilterType {
		NONE, ONE_INVALID, ALL_INVALID
	}
	
	public DataFrame(MasterFrame parent, JDesktopPane desktop, Instrument instrument) {
		super(parent, desktop);
		this.instrument = instrument;
		setFrameIconByName("data.gif");
		init();
	}
	public static void setFilterType(RowFilter<DataTableModel,Integer> filter, JDesktopPane desktopPane) {
		if (currentFilter != filter) {
			currentFilter = filter;
			for (int i=0; i<desktopPane.getComponentCount(); i++ ){
				Component cmp = desktopPane.getComponent(i);
				if (cmp instanceof DataFrame) {
					((DataFrame) cmp).setFilterTypeUpdate();
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private void setFilterTypeUpdate() {
		((TableRowSorter<DataTableModel>) table.getRowSorter()).setRowFilter(currentFilter);
	}

	private void init() {
		setTitle("Data for " + instrument.name + " (" + instrument.symbol + ")");
		table = new JTable(new DataTableModel(instrument));
		table.setDefaultRenderer(Date.class, new DateRenderer("yyyy-MM-dd EEE", "yyyy-MM-dd HH:mm:ss"));
		table.setDefaultRenderer(NumberDate.class, new DateRenderer("yyyy-MM-dd EEE", "yyyy-MM-dd HH:mm:ss"));
		table.setDefaultRenderer(Float.class, new FloatRenderer());
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setAutoCreateRowSorter(true);
		setFilterTypeUpdate();
		JScrollPane pane = new JScrollPane(table);
		getContentPane().add(pane);
		setVisible(true);
		pack();
	}

}