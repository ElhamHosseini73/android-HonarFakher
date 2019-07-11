package ir.rayapars.honarfakher.fragments;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.orm.query.Select;
import java.util.List;
import ir.rayapars.honarfakher.BR;
import ir.rayapars.honarfakher.R;
import ir.rayapars.honarfakher.activities.MainActivity;
import ir.rayapars.honarfakher.adapters.UniversalAdapter2;
import ir.rayapars.honarfakher.classes.App;
import ir.rayapars.honarfakher.classes.CheckoutOrder;
import ir.rayapars.honarfakher.classes.ProductDetails;
import ir.rayapars.honarfakher.classes.Setting;
import ir.rayapars.honarfakher.databinding.FragmentShoppingCartBinding;
import ir.rayapars.honarfakher.databinding.ItemsShoppingCartBinding;

public class ShoppingCartFragment extends Fragment {

    View x;
    public List<CheckoutOrder> checkoutOrderList;
    int sum = 0;
    UniversalAdapter2 adapter;
    FragmentShoppingCartBinding binding;
    public List<ProductDetails> shoppingCartProducts;
    MainActivity mainActivity = new MainActivity();

    int countProduct = 0;

    long finalPrice = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentShoppingCartBinding.inflate(getLayoutInflater());
        x = binding.getRoot();
        x.setFocusable(true);
        x.setClickable(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        binding.myRecyclerView.setLayoutManager(layoutManager);


        adapter = new UniversalAdapter2(R.layout.items_shopping_cart, App.pd, BR.var);
        binding.myRecyclerView.setAdapter(adapter);

        for (int i = 0; i < App.pd.size(); i++) {

            finalPrice += App.pd.get(i).numberOfProduct * Integer.parseInt(App.pd.get(i).final_price);
        }
        binding.finalPrice.setText(String.valueOf(String.format("%,d", finalPrice)) + " تومان ");

        adapter.setOnItemBindListener(new UniversalAdapter2.OnItemBindListener() {
            @Override
            public void onBind(final ViewDataBinding binding1, final int position) {

                final ItemsShoppingCartBinding b = ((ItemsShoppingCartBinding) binding1);


                int totalPrice = App.pd.get(position).numberOfProduct * Integer.parseInt(App.pd.get(position).final_price);
                b.priceFinal.setText(String.valueOf(String.format("%,d", totalPrice)) + "تومان ");

                b.add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (Integer.parseInt(App.pd.get(position).getQuantity()) > App.pd.get(position).numberOfProduct) {

                            int count = App.pd.get(position).numberOfProduct + 1;
                            b.numberOfProducts.setText(String.valueOf(count));
                            int totalPrice = count * Integer.parseInt(App.pd.get(position).final_price);
                            b.priceFinal.setText(String.valueOf(String.format("%,d", totalPrice)) + "تومان");
                            App.pd.get(position).numberOfProduct = count;
                            finalPrice += Integer.parseInt(App.pd.get(position).final_price);
                            binding.finalPrice.setText(String.valueOf(String.format("%,d", finalPrice)) + " تومان ");
                        } else {

                            Toast.makeText(getContext(), "بیشتر از  " + App.pd.get(position).getQuantity() + " تا نمی توان انتخاب کرد", Toast.LENGTH_SHORT).show();

                        }


                    }
                });

                b.min.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int count = 0;
                        int totalPrice;

                        for (int i = 0; i < App.pd.size(); i++) {

                            if (App.pd.get(i).id.equals(App.pd.get(position).id)) {

                                count = App.pd.get(position).numberOfProduct;
                            }

                        }
                        count -= 1;

                        if (count <= 1) {

                            App.pd.get(position).numberOfProduct = 1;
                            countProduct = 1;
                            totalPrice = 1 * Integer.parseInt(App.pd.get(position).final_price);
                            b.numberOfProducts.setText("1");
                            b.priceFinal.setText(String.valueOf(String.format("%,d", totalPrice)) + " تومان");
                            finalPrice = Integer.parseInt(App.pd.get(position).final_price);
                            binding.finalPrice.setText(String.valueOf(String.format("%,d", finalPrice)) + " تومان ");

                        } else {

                            totalPrice = count * Integer.parseInt(App.pd.get(position).final_price);
                            b.priceFinal.setText(String.valueOf(String.format("%,d", totalPrice)) + " تومان");
                            b.numberOfProducts.setText(String.valueOf(count));
                            App.pd.get(position).numberOfProduct = count;
                            finalPrice -= Integer.parseInt(App.pd.get(position).final_price);
                            binding.finalPrice.setText(String.valueOf(String.format("%,d", finalPrice)) + " تومان ");

                        }

                    }
                });


                b.removeItemFromTheCart.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {


                        int value = App.pd.get(position).numberOfProduct * Integer.parseInt(App.pd.get(position).final_price);
                        binding.finalPrice.setText(String.valueOf(String.format("%,d", finalPrice - value)) + " تومان ");

                        App.pd.remove(position);
                        adapter.notifyItemRemoved(position);
                        adapter.notifyDataSetChanged();
                        checkCart();


                    }
                });
            }
        });

        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        binding.btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Setting s = Select.from(Setting.class).first();
                if (s != null) {
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    PaymentFragment informationFragment = new PaymentFragment();
                    informationFragment.price = finalPrice;
                    ft.add(R.id.frame_layout, informationFragment).addToBackStack("").commit();


                } else {
                    Toast.makeText(getContext(), "لطفا ابتدا وارد حساب کاربری خود شوید", Toast.LENGTH_LONG).show();
                }
            }
        });

        return x;
    }

    public void checkCart() {
        if (App.pd.size() < 1) {
            FragmentTransaction transactionCart = getFragmentManager().beginTransaction();
            transactionCart.add(R.id.frame_layout, new CartFragment()).addToBackStack("").commit();
        }
    }

    public long getPrice() {
        sum = 0;
        try {

            for (int i = 0; i < App.pd.size(); i++) {
                sum += Integer.parseInt(App.pd.get(i).final_price) * App.pd.get(i).numberOfProduct;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sum;
    }


}
