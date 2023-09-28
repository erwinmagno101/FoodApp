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


public class Burger extends Fragment implements View.OnClickListener {

    View view;
    String ID;
    DBHelper DB;
    Button burbtnAdd, burbtnAdd2, burbtnAdd3, burbtnBack;
    TextView burtxtItemName, burtxtItemName2, burtxtItemName3;
    TextView burtxtItemPrize, burtxtItemPrize2, burtxtItemPrize3;
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
        view = inflater.inflate(R.layout.fragment_burger, container, false);

        ID = LogInSection.ID;
        DB = new DBHelper(getContext());
        burbtnAdd = view.findViewById(R.id.burbtnAdd);
        burbtnAdd.setOnClickListener(this);
        burbtnAdd2 = view.findViewById(R.id.burbtnAdd2);
        burbtnAdd2.setOnClickListener(this);
        burbtnAdd3 = view.findViewById(R.id.burbtnAdd3);
        burbtnAdd3.setOnClickListener(this);
        burbtnBack = view.findViewById(R.id.burbtnBack);
        burbtnBack.setOnClickListener(this);
        burtxtItemName = view.findViewById(R.id.burtxtItemName);
        burtxtItemName2 = view.findViewById(R.id.burtxtItemName2);
        burtxtItemName3 = view.findViewById(R.id.burtxtItemName3);
        burtxtItemPrize = view.findViewById(R.id.burtxtItemPrice);
        burtxtItemPrize2 = view.findViewById(R.id.burtxtItemPrice2);
        burtxtItemPrize3 = view.findViewById(R.id.burtxtItemPrice3);

        item1 = new Item("Ultimate Veggie", 200);
        burtxtItemName.setText(item1.getItemName());
        burtxtItemPrize.setText("₱ " + Integer.toString(item1.getPrice()));
        item2 = new Item("Triple Decker", 250);
        burtxtItemName2.setText(item2.getItemName());
        burtxtItemPrize2.setText("₱ " + Integer.toString(item2.getPrice()));
        item3 = new Item("Insanity Burger", 300);
        burtxtItemName3.setText(item3.getItemName());
        burtxtItemPrize3.setText("₱ " + Integer.toString(item3.getPrice()));
        return view;
    }

    @Override
    public void onClick(View view) {
        if(view == burbtnAdd){
            ordername = item1.itemName.toString();
            onCreateQuantityPopupDialog(item1);
        }
        if(view == burbtnAdd2){
            ordername = item2.itemName.toString();
            onCreateQuantityPopupDialog(item2);
        }
        if(view == burbtnAdd3){
            ordername = item3.itemName.toString();
            onCreateQuantityPopupDialog(item3);
        }
        if(view == burbtnBack){
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