Create table plans (
"id" INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
"carrier_name" VARCHAR(15) NOT NULL,
"plan_name" VARCHAR(15) NOT NULL,
"unlimited_texts" BOOLEAN NOT NULL,
"unlimited_calls" BOOLEAN NOT NULL,
"unlimited_data" BOOLEAN NOT NULL,
"texts_are_minutes" BOOLEAN NOT NULL,
"hard_cap" BOOLEAN NOT NULL,
"minutes_per_month" INTEGER NOT NULL,
"texts_per_month" INTEGER NOT NULL,
"data_gb_per_month" DECIMAL(3,1) NOT NULL,
"monthy_price" DECIMAL(3,2) NOT NULL
);