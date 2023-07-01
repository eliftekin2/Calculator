package com.eliftekin.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //değişkenler
    Button one, two, three, four, five, six, seven, eight, nine, zero;
    Button clear, division, multiply, minus, plus, equal, bracket, percent;
    TextView giris, sonuc;

    boolean checkBracket = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //textviewlerin idlerini değişkenlere atar
        giris = findViewById(R.id.giris);
        sonuc = findViewById(R.id.sonuc);

        //butonların idlerini değişkenlere atayan fonksiyonu çağırır
        AssaignID(one, R.id.one);
        AssaignID(two, R.id.two);
        AssaignID(three, R.id.three);
        AssaignID(four, R.id.four);
        AssaignID(five, R.id.five);
        AssaignID(six, R.id.six);
        AssaignID(seven, R.id.seven);
        AssaignID(eight, R.id.eight);
        AssaignID(nine, R.id.nine);
        AssaignID(zero, R.id.zero);

        AssaignID(clear, R.id.clear);
        AssaignID(bracket, R.id.bracket);
        AssaignID(percent, R.id.percent);
        AssaignID(division, R.id.division);
        AssaignID(multiply, R.id.multiply);
        AssaignID(minus, R.id.minus);
        AssaignID(plus, R.id.plus);
        AssaignID(equal, R.id.equal);

    }

    //button tipindeki değişkenlere idleri atar
    void AssaignID(Button btn, int id)
    {
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    //bir butona basıldığında
    @Override
    public void onClick(View view) {
        Button buton = (Button) view;
        //butondaki yazan değeri alıp butonText'e atar
        String butonText = buton.getText().toString();
        //giris'deki değerleri alır stringe dönüştürüp data'ya atar
        String data = giris.getText().toString();

        //eğer c butonuna tıklanırsa
        if (butonText.equals("c"))
        {
            //giris'deki ve sonuc'daki tüm değerleri siler
            giris.setText("");
            sonuc.setText("");
            return;
        }

        //eğer parantez butonuna basılmışsa
        if (butonText.equals("()"))
        {
            //parantez açılmış mı diye kontrol eden değişkenin değeri true ise (yani açık parantez kullanılmışsa)
            if (checkBracket)
            {
                //kapalı parantez yazıp değişkenin değerini başlangıç değerine döndürür.
                giris.setText(giris.getText()+")");
                checkBracket = false;
            }
            //eğer parantez hiç açılmamışsa
            else if (!checkBracket)
            {
                if (!data.isEmpty())
                {
                    //girilmiş verileri char dizisine dönüştürür.
                    data.toCharArray();
                    //dizinin son elemanını alır
                    char dizi = data.charAt(data.length()-1);
                    //karşılaştırma yapabilmek için stringe sönüştürür
                    String son = String.valueOf(dizi);
                    if (son.equals("1")|| son.equals("2")|| son.equals("3")|| son.equals("4")|| son.equals("5")|| son.equals("6")|| son.equals("7")|| son.equals("8")|| son.equals("9")|| son.equals("0"))
                    {
                        giris.setText(giris.getText()+"x"+"(");
                        checkBracket = true;
                    }
                }
                else
                {
                    //açık parantez yazdırır
                    giris.setText(giris.getText()+"(");
                    checkBracket = true;
                }
            }
            return;
        }

        //yüzde butonuna tıklandığında
        if (butonText.equals("%"))
        {
            //önce daha önceden veri girişi yapılıp yapılmadığını kontrol eder. Veri girişi yapılmışsa
            if(!data.isEmpty())
            {
                //girilmiş verileri char dizisine dönüştürür.
                data.toCharArray();
                //dizinin son elemanını alır
                char dizi = data.charAt(data.length()-1);
                //karşılaştırma yapabilmek için stringe sönüştürür
                String son = String.valueOf(dizi);
                //eğer son girilmiş değer herhangi bir matematik işlemiyse bir şey yapma
                if (son.equals("/") || son.equalsIgnoreCase("x") || son.equals("+") || son.equals("-"))
                {
                    giris.setText(giris.getText()+"");
                    return;
                }
                //eğer son girilmiş değer bir rakamsa o zaman yanına yüzde işareti koy
                else
                    giris.setText(giris.getText()+"%"); return;
            }
            //eğer daha önceden veri girilmemişse bir şey yapma
            else
                giris.setText(giris.getText()+""); return;
        }

        if (butonText.equals("="))
        {
            data = data.replace("x", "*");
            data = data.replace("%", "/100");

            sonuc.setText(calculate(data));
        }

        else
            giris.setText(giris.getText()+butonText);
    }

    String calculate(String data)
    {
        try
        {
            Context context = Context.enter();
            context.setOptimizationLevel(-1);

            Scriptable scriptable = context.initStandardObjects();
            String result = context.evaluateString(scriptable,data,"javascript",1,null).toString();
            return result;
        }
        catch (Exception e)
        {
            return "Hata";
        }
    }
}