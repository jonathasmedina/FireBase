package com.example.jonathas.firebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.*;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private EditText nome_EditText, endereco_EditText;
    private Button bt;
    private TextView tv;
//    int qtd=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nome_EditText = (EditText) findViewById(R.id.editText);
        endereco_EditText = (EditText) findViewById(R.id.editText2);
        bt = (Button) findViewById(R.id.button);
        tv = (TextView) findViewById(R.id.textView2);


        Firebase.setAndroidContext(this);


        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //qtd++;
                //1 - Criar um objeto firebase
                Firebase objetoFirebase = new Firebase(Config.FIREBASE_URL);

                //2 - Valores a armazenar
                String nome = nome_EditText.getText().toString().trim();
                String endereco = endereco_EditText.getText().toString().trim();

                //3 - Criando o objeto Pessoa e adicionando os valores a ele
                Pessoa pessoa = new Pessoa();
                pessoa.setNome(nome);
                pessoa.setEndereco(endereco);

               // String p = Integer.toString(qtd);
                //4 - Armazenando valores ao FireBase
                objetoFirebase.child("Pessoa").setValue(pessoa);


                objetoFirebase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for(DataSnapshot postSnapShot: dataSnapshot.getChildren()){
                            //recuperando do snapshot
                            Pessoa pessoa = postSnapShot.getValue(Pessoa.class);
                            //adicionando à uma String
                            String c = "Nome: " + pessoa.getNome()+
                                        "\nEndereço: " + pessoa.getEndereco() + "\n\n";

                            //exibindo na textview
                            tv.setText(c);
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                        System.out.println("A leitura falhou." + firebaseError.getMessage());
                    }
                });

            }
        });

    }
}
