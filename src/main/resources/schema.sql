
drop table if exists recipe_instruction;
drop table if exists recipe_ingredient;
drop table if exists ingredient;
drop table if exists recipe;
CREATE TABLE ingredient (
  ingredient_id INT AUTO_INCREMENT  PRIMARY KEY,
  ingredient_name VARCHAR(250) NOT NULL,
  is_active VARCHAR(2) not null,
  created_by VARCHAR(30) default 'System',
  created_date date default CURRENT_DATE,
  updated_by VARCHAR(30) default 'System',
  updated_date date default CURRENT_DATE
  );
 
 CREATE TABLE recipe (
  recipe_id INT AUTO_INCREMENT  PRIMARY KEY,
  recipe_name VARCHAR(250) NOT NULL,
  is_veg VARCHAR(2) not null,
  no_of_servings NUMERIC not null,
  is_active VARCHAR(2) not null,
  created_by VARCHAR(30) not null default 'System',
  created_date date not null  default CURRENT_DATE,
  updated_by VARCHAR(30) not null default 'System',
  updated_date date not null  default CURRENT_DATE
  );
  
  CREATE TABLE recipe_instruction (
   instructions_id INT AUTO_INCREMENT  PRIMARY KEY,
   recipe_id INT NOT NULL,
   cooking_method VARCHAR(50) NOT NULL,
   cooking_steps VARCHAR(2000) NOt NULL,
   additional_info VARCHAR(1000) NULL,
   CONSTRAINT fk_recipe  FOREIGN KEY (recipe_id) REFERENCES recipe(recipe_id)
   );

 
  CREATE TABLE recipe_ingredient (
 	recipe_id INT NOT NULL,
   ingredient_id INT NOT NULL,
  CONSTRAINT pk_recipe_ingredient  PRIMARY KEY (ingredient_id,recipe_id)
  );
  

 
  
  