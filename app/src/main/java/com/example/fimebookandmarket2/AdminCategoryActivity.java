package com.example.fimebookandmarket2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AdminCategoryActivity extends AppCompatActivity {

    private ImageView papeleo, leds, calculator;
    private ImageView laptop, proyector, bata;
    private ImageView componentes, material, libros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);

        papeleo = (ImageView) findViewById(R.id.papeleo);
        leds = (ImageView) findViewById(R.id.leds);
        calculator = (ImageView) findViewById(R.id.calculator);

        laptop = (ImageView) findViewById(R.id.laptop);
        proyector = (ImageView) findViewById(R.id.proyector);
        bata = (ImageView) findViewById(R.id.bata);

        componentes = (ImageView) findViewById(R.id.componentes);
        material = (ImageView) findViewById(R.id.material);
        libros = (ImageView) findViewById(R.id.libro);

        papeleo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("Categoria", "Papeleo de FIME");
                startActivity(intent);
            }
        });

        leds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("Categoria", "Electronica");
                startActivity(intent);
            }
        });

        calculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("Categoria", "Material de Clase");
                startActivity(intent);
            }
        });

        laptop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("Categoria", "Material de Software");
                startActivity(intent);
            }
        });

        proyector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("Categoria", "Material de Exposicion");
                startActivity(intent);
            }
        });

        bata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("Categoria", "Material de Quimica");
                startActivity(intent);
            }
        });

        componentes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("Categoria", "Componentes");
                startActivity(intent);
            }
        });

        material.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("Categoria", "Materiales de Taller");
                startActivity(intent);
            }
        });

        libros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("Categoria", "Libros");
                startActivity(intent);
            }
        });
    }
}
