package com.example.IntegrationExample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

public class PrinterFragment extends Fragment {
	private RadioGroup rgInvoiceType, rgInputType;
	private EditText edtIntroduce;
	private Button btnAction, btnOpenShift, btnCloseShift, btnXReport, btnYReport, btnReport1, btnPrintText;
	private EditText txtResult;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_printer, container, false);

		rgInvoiceType = (RadioGroup)view.findViewById(R.id.rgInvoiceType);
		rgInputType = (RadioGroup)view.findViewById(R.id.rgInputType);
		edtIntroduce = (EditText)view.findViewById(R.id.edtIntroduce);
		btnAction = (Button)view.findViewById(R.id.btnFiscalAction);
		btnOpenShift = (Button)view.findViewById(R.id.btnOpenShift);
        btnCloseShift = (Button)view.findViewById(R.id.btnCloseShift);
		btnXReport = (Button)view.findViewById(R.id.btnXReport);
		btnYReport = (Button)view.findViewById(R.id.btnYReport);
		btnReport1 = (Button)view.findViewById(R.id.btnReport1);
		btnPrintText = (Button)view.findViewById(R.id.btnPrintText);
        txtResult = (EditText)view.findViewById(R.id.txtResult);

		btnAction.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
                fiscalAction();
			}
		});
        btnOpenShift.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				openShift();
			}
		});
        btnCloseShift.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				closeShift();
			}
		});
		btnXReport.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				xReport();
			}
		});
		btnYReport.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				yReport();
			}
		});
		btnReport1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				report1();
			}
		});
		btnPrintText.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				printText();
			}
		});

		return view;
	}
	
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 503) {
        	if (resultCode == Activity.RESULT_OK) {
        		txtResult.setText("Shift was opened");
        	} else 
        		txtResult.setText("Cant open shift");
        }
        
        if (requestCode == 504) {
        	if (resultCode == Activity.RESULT_OK) {
        		if (data.getExtras().containsKey("Registers"))
                    txtResult.setText("Registers :\n" + data.getExtras().getString("Registers"));
        	} else 
        		txtResult.setText("Cant close shift");
        }

		if (requestCode == 505) {
			if (resultCode == Activity.RESULT_OK) {
				String strResult = "";
				if (data.getExtras().containsKey("FiscalPrinterSN"))
					strResult += "FiscalPrinterSN : " +  data.getExtras().getString("FiscalPrinterSN") + "\n";

				if (data.getExtras().containsKey("FiscalShift"))
					strResult += "FiscalShift : " +  data.getExtras().getString("FiscalShift") + "\n";

				if (data.getExtras().containsKey("FiscalCryptoVerifCode"))
					strResult += "FiscalCryptoVerifCode : " +  data.getExtras().getString("FiscalCryptoVerifCode") + "\n";

				if (data.getExtras().containsKey("FiscalDocSN"))
					strResult += "FiscalDocSN : " +  data.getExtras().getString("FiscalDocSN") + "\n";

				if (data.getExtras().containsKey("FiscalDocumentNumber"))
					strResult += "FiscalDocumentNumber : " +  data.getExtras().getString("FiscalDocumentNumber") + "\n";

				if (data.getExtras().containsKey("FiscalStorageNumber"))
					strResult += "FiscalStorageNumber : " +  data.getExtras().getString("FiscalStorageNumber") + "\n";

				if (data.getExtras().containsKey("FiscalMark"))
					strResult += "FiscalMark : " +  data.getExtras().getString("FiscalMark") + "\n";

				if (data.getExtras().containsKey("FiscalDatetime"))
					strResult += "FiscalDatetime : " +  data.getExtras().getString("FiscalDatetime") + "\n";

				txtResult.setText(strResult);
			} else {
				if (data != null && data.getExtras().containsKey("ErrorMessage"))
					txtResult.setText(data.getExtras().getString("ErrorMessage"));
				else
					txtResult.setText("Платеж не проведен!");
			}
		}

		if (requestCode == 506) {
			if (resultCode == Activity.RESULT_OK) {
				txtResult.setText("X-Report was printed");
			} else
				txtResult.setText("Failed to print X-Report");
		}

        if (requestCode == 507) {
            if (resultCode == Activity.RESULT_OK) {
                txtResult.setText("Y-Report was printed");
            } else
                txtResult.setText("Failed to print Y-Report");
        }

		if (requestCode == 508) {
			if (resultCode == Activity.RESULT_OK) {
				txtResult.setText("Report1 was printed");
			} else
				txtResult.setText("Failed to print Report1");
		}

		if (requestCode == 509) {
			if (resultCode == Activity.RESULT_OK) {
				txtResult.setText("Text was printed");
			} else
				txtResult.setText("Failed to print text");
		}
    }
	
    private void openShift() {
    	Intent intent = new Intent("ru.toucan.terminal.printer");
    	intent.putExtra("Email", getString(R.string.login));
        intent.putExtra("Password", getString(R.string.password));
        intent.putExtra("Action", "OpenShift");

		try {
			double introduce = Double.parseDouble(edtIntroduce.getText().toString());
			if (introduce > 0d) {
				intent.putExtra("Cash", introduce);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
        startActivityForResult(intent, 503);
    }
    
    private void closeShift() {
    	Intent intent = new Intent("ru.toucan.terminal.printer");
    	intent.putExtra("Email", getString(R.string.login));
        intent.putExtra("Password", getString(R.string.password));
        intent.putExtra("Action", "CloseShift");
		intent.putExtra("ReadRegisters", false);
        startActivityForResult(intent, 504);
    }

	private void fiscalAction() {
		String purchases =
			"{"+
				"\"Purchases\": [{"+
				"    \"Title\": \"Позиция без ндс \","+
				"            \"Price\": 111.256,"+
				"            \"Quantity\": 2,"+
				"           \"TaxCode\": []"+
				"}]"+
			"}";

		Intent intent = new Intent("ru.toucan.terminal.printer");
        intent.putExtra("Email", getString(R.string.login));
        intent.putExtra("Password", getString(R.string.password));
		intent.putExtra("ReceiptEmail", "test@test.com");
		//intent.putExtra("ReceiptPhone", "+79161112233");
		String invoiceType = "Sale";
		if (rgInvoiceType.getCheckedRadioButtonId() != -1) {
			switch (rgInvoiceType.indexOfChild(rgInvoiceType.findViewById(rgInvoiceType.getCheckedRadioButtonId()))) {
				case 0:
					invoiceType = "Sale";
					break;
				case 1:
					invoiceType = "Return";
					break;
			}
		}
		intent.putExtra("Action", invoiceType);
		if (rgInputType.getCheckedRadioButtonId() != -1) {
			String inputType = null;
			switch (rgInputType.indexOfChild(rgInputType.findViewById(rgInputType.getCheckedRadioButtonId()))) {
				case 0:
					inputType = "CASH";
					break;
				case 1:
					inputType = "CARD";
					break;
				case 2:
					inputType = "PREPAID";
					break;
			}
			if (inputType != null)
				intent.putExtra("InputType", inputType);
		}
		intent.putExtra("Purchases", purchases);
		try {
            double cashAccepted = Double.parseDouble(edtIntroduce.getText().toString());
            if (cashAccepted > 0d) {
                intent.putExtra("Cash", cashAccepted);
                startActivityForResult(intent, 505);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
	}

	private void xReport() {
		Intent intent = new Intent("ru.toucan.terminal.printer");
		intent.putExtra("Email", getString(R.string.login));
		intent.putExtra("Password", getString(R.string.password));
		intent.putExtra("Action", "XReport");
		startActivityForResult(intent, 506);
	}

	private void yReport() {
		Intent intent = new Intent("ru.toucan.terminal.printer");
		intent.putExtra("Email", getString(R.string.login));
		intent.putExtra("Password", getString(R.string.password));
		intent.putExtra("Action", "YReport");
		startActivityForResult(intent, 507);
	}

	private void report1() {
		Intent intent = new Intent("ru.toucan.terminal.printer");
		intent.putExtra("Email", getString(R.string.login));
		intent.putExtra("Password", getString(R.string.password));
		intent.putExtra("Action", "Report1");
		startActivityForResult(intent, 508);
	}

	private void printText() {
		Intent intent = new Intent("ru.toucan.terminal.printer");
		intent.putExtra("Email", getString(R.string.login));
		intent.putExtra("Password", getString(R.string.password));
		intent.putExtra("Action", "PrintText");
		intent.putExtra("Text", "Lorem ipsum dolor sit amet, consectetur adipiscing elit,\nsed do eiusmod tempor\nincididunt ut labore et dolore magna aliqua.");
		startActivityForResult(intent, 509);
	}
}
