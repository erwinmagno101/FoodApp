package com.example.foodapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;


public class Recommended extends Fragment implements View.OnClickListener {

    View view;
    String ID;
    DBHelper DB;
    Button recbtnAdd, recbtnAdd2, recbtnAdd3, recbtnBack;
    TextView rectxtItemName, rectxtItemName2, rectxtItemName3;
    TextView rectxtItemPrize, rectxtItemPrize2, rectxtItemPrize3;
    HomeFragment homeFragment;
    Item item1, item2, item3;
    String ordername;


    int ordercount;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private TextView txtQuantityItem, txtQuantityNum;
    private Button btnDecrease, btnIncrease, btnConfirmAdd;
    private  View lineQuantity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_recommended, container, false);

        ID = LogInSection.ID;
        DB = new DBHelper(getContext());
        recbtnAdd = view.findViewById(R.id.recbtnAdd);
        recbtnAdd.setOnClickListener(this);
        recbtnAdd2 = view.findViewById(R.id.recbtnAdd2);
        recbtnAdd2.setOnClickListener(this);
        recbtnAdd3 = view.findViewById(R.id.recbtnAdd3);
        recbtnAdd3.setOnClickListener(this);
        recbtnBack = view.findViewById(R.id.recbtnBack);
        recbtnBack.setOnClickListener(this);
        rectxtItemName = view.findViewById(R.id.rectxtItemName);
        rectxtItemName2 = view.findViewById(R.id.rectxtItemName2);
        rectxtItemName3 = view.findViewById(R.id.rectxtItemName3);
        rectxtItemPrize = view.findViewById(R.id.rectxtItemPrice);
        rectxtItemPrize2 = view.findViewById(R.id.rectxtItemPrice2);
        rectxtItemPrize3 = view.findViewById(R.id.rectxtItemPrice3);

        item1 = new Item("Hawaiian Pizza", 350);
        rectxtItemName.setText(item1.getItemName());
        rectxtItemPrize.setText("₱ " + Integer.toString(item1.getPrice()));
        item2 = new Item("Chicken Salad Sandwich", 100);
        rectxtItemName2.setText(item2.getItemName());
        rectxtItemPrize2.setText("₱ " + Integer.toString(item2.getPrice()));
        item3 = new Item("Strawberry Basil", 100);
        rectxtItemName3.setText(item3.getItemName());
        rectxtItemPrize3.setText("₱ " + Integer.toString(item3.getPrice()));

        return view;
    }

    @Override
    public void onClick(View view) {
        if(view == recbtnAdd){
            ordername = item1.itemName.toString();
            onCreateQuantityPopupDialog(item1);
        }
        if(view == recbtnAdd2){
            ordername = item2.itemName.toString();
            onCreateQuantityPopupDialog(item2);
        }
        if(view == recbtnAdd3){
            ordername = item3.itemName.toString();
            onCreateQuantityPopupDialog(item3);
        }
        if(view == recbtnBack){
            Fragment fragment = new HomeFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left,R.anim.enter_right_to_left,R.anim.exit_right_to_left);
            fragmentTransaction.replace(R.id.frameLayout,fragment);
            fragmentTransaction.commit();
        }
    }
    public void onCreateQuantityPopupDialog(Item item){

        dialogBuilder = new AlertDialog.Builder(getContext());
        final View quantityPopupView = getLayoutInflater().inflate(R.layout.quantity_popup, null);
        txtQuantityItem = quantityPopupView.findViewById(R.id.txtQuantityItem);
        txtQuantityNum = quantityPopupView.findViewById(R.id.txtQuantityNum);
        btnDecrease = quantityPopupView.findViewById(R.id.btnDecrease);
        btnIncrease = quantityPopupView.findViewById(R.id.btnIncrease);
        btnConfirmAdd = quantityPopupView.findViewById(R.id.btnConfirmAdd);
        lineQuantity = quantityPopupView.findViewById(R.id.lineQuantity);

        dialogBuilder.setView(quantityPopupView);
        dialog = dialogBuilder.create();
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.BOTTOM;
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setAttributes(wlp);
        dialog.show();

        ordercount = 1;
        txtQuantityNum.setText(Integer.toString(ordercount));
        txtQuantityItem.setText(ordername);

        btnDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ordercount > 1){
                    ordercount -= 1;
                }
                txtQuantityNum.setText(Integer.toString(ordercount));
            }
        });

        btnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ordercount < 9){
                    ordercount += 1;
                }
                txtQuantityNum.setText(Integer.toString(ordercount));
            }
        });

        btnConfirmAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(LogInSection.ID == null){

                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    Intent intent = new Intent(getActivity(), LogInSection.class);
                                    startActivity(intent);
                                    getActivity().finish();
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    //No button clicked
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("You need to Log in to add items to cart.").setPositiveButton("Sign in", dialogClickListener)
                            .setNegativeButton("Cancel", dialogClickListener).show();

                }else{
                    Random rand = new Random();
                    int idnum = rand.nextInt(999999999);
                    String itemid = Integer.toString(idnum);
                    String email = ID.toString();
                    String itemname = item.getItemName().toString();
                    String price = Integer.toString(item.getPrice() * ordercount);
                    String quantity = Integer.toString(ordercount);
                    Boolean check = DB.checkItem(itemid);
                    if(check == false){
                        Boolean insert = DB.insertItem(itemid,email,itemname,price,quantity);
                        if (insert == true){
                            Toast.makeText(getContext(), "Item Added to Cart", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }else{
                            Toast.makeText(getContext(), "Adding Failed, Please Retry", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getContext(), "Same ID", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });



    }
}