package com.example.jonathas.firebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.*;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private EditText nome_EditText, endereco_EditText;
    private Button bt;
    private TextView tv;
    private ListView listView;
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
                pessoa.setId(UUID.randomUUID().toString());

               // String p = Integer.toString(qtd);
                //4 - Armazenando valores ao FireBase

                //sobrescreve sempre o objeto "Pessoa" no banco
                //objetoFirebase.child("Pessoa").setValue(pessoa);

                //adiciona um novo objeto - sem sobrescrever
                objetoFirebase.child(pessoa.getNome()).setValue(pessoa);

                //hierarquia
                //objetoFirebase.child("Pessoa").child(pessoa.getNome()).setValue(pessoa);

                objetoFirebase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //adicionar retorno em um array de string
                        ArrayList<String> pArray = new ArrayList<>();

                        for(DataSnapshot postSnapShot: dataSnapshot.getChildren()){
                            //recuperando do snapshot
                            Pessoa pessoa = postSnapShot.getValue(Pessoa.class);
                            //adicionando o array de strings
                            pArray.add("Nome: " + pessoa.getNome() + "| Endereço: " + pessoa.getEndereco() +"\n");

                            mostrarLista(pArray);

                            //exibindo na textview
                            //tv.setText(c);
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

    private void mostrarLista(ArrayList<String> pArray) {
        if(pArray != null){
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, pArray);
            listView = (ListView) findViewById(R.id.listView);
            listView.setAdapter(adapter);
        }
    }
}
