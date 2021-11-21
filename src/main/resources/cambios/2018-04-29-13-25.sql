/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Max Dicson
 * Created: 29-abr-2018
 */

ALTER TABLE `scorpio`.`concepto_gasto` 
ADD COLUMN `empresa_id` INT(11) NULL DEFAULT NULL AFTER `estado_id`,
ADD INDEX `fk_concepto_gasto_empresa1_idx` (`empresa_id` ASC);

CREATE TABLE IF NOT EXISTS `scorpio`.`empresa` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `codigo` VARCHAR(45) NULL DEFAULT NULL,
  `descripcion` VARCHAR(250) NULL DEFAULT NULL,
  `estado` TINYINT(4) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

ALTER TABLE `scorpio`.`concepto_gasto` 
ADD CONSTRAINT `fk_concepto_gasto_empresa1`
  FOREIGN KEY (`empresa_id`)
  REFERENCES `scorpio`.`empresa` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;