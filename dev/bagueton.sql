-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : db
-- Généré le : dim. 21 avr. 2024 à 09:58
-- Version du serveur : 8.3.0
-- Version de PHP : 8.2.18

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `bagueton`
--

-- --------------------------------------------------------

--
-- Structure de la table `bagueton_recipe`
--

CREATE TABLE `bagueton_recipe` (
  `id_recipe` int NOT NULL,
  `title` varchar(20) NOT NULL,
  `image` varchar(255) DEFAULT NULL,
  `steps` text,
  `ingredients` text
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `bagueton_recipe`
--

INSERT INTO `bagueton_recipe` (`id_recipe`, `title`, `image`, `steps`, `ingredients`) VALUES
(1, 'Pain Classique', 'http://90.51.140.217:8081/baguette.png', '1. Mélanger farine, eau, sel, levure. 2. Pétrir la pâte. 3. Laisser reposer 1h. 4. Façonner et cuire à 230°C pendant 25min.', '500g de farine, 300ml d’eau tiède, 10g de sel, 7g de levure sèche'),
(2, 'Croissants', 'http://90.51.140.217:8081/croissant.png', '1. Mélanger farine, lait, sucre, levure. 2. Incorporer beurre. 3. Reposer. 4. Rouler en triangles. 5. Cuire à 200°C pendant 20min.', '500g de farine, 250ml de lait, 75g de sucre, 10g de levure, 250g de beurre'),
(3, 'Brioche Tressée', 'http://90.51.140.217:8081/brioche.png', '1. Mélanger farine, sucre, levure, oeufs. 2. Ajouter beurre. 3. Laisser lever 2h. 4. Tresser et cuire à 180°C pendant 25min.', '250g de farine, 30g de sucre, 5g de sel, 10g de levure fraîche, 3 oeufs, 125g de beurre mou'),
(4, 'Focaccia', 'http://90.51.140.217:8081/focaccia.png', '1. Diluer levure dans eau. 2. Mélanger farine, sel, eau-levure, huile. 3. Pétrir. 4. Laisser reposer 1h. 5. Parsemer de romarin, gros sel. 6. Cuire à 220°C pendant 20min.', '500g de farine, 300ml d\'eau tiède, 10g de sel, 15g de levure fraîche, 50ml d\'huile d\'olive, romarin, gros sel'),
(5, 'Pain Complet', 'http://90.51.140.217:8081/campagne.jpg', '1. Mélanger farines, sel, levure. 2. Ajouter eau, pétrir. 3. Laisser lever 1h. 4. Façonner, cuire à 210°C pendant 30min.', '400g de farine complète, 100g de farine blanche, 350ml d\'eau tiède, 10g de sel, 7g de levure sèche'),
(6, 'Bagels', 'http://90.51.140.217:8081/bagel.png', '1. Mélanger farine, sucre, sel, levure, eau. 2. Pétrir, laisser lever 1h. 3. Façonner en bagels, bouillir chaque côté. 4. Cuire à 220°C pendant 20min.', '500g de farine, 250ml d\'eau tiède, 50g de sucre, 10g de sel, 10g de levure sèche, 1 oeuf pour badigeonner, graines de sésame pour garnir'),
(16, 'dddd', 'http://90.51.140.217:8081/logo.png', 'ddd', 'fff');

-- --------------------------------------------------------

--
-- Structure de la table `images`
--

CREATE TABLE `images` (
  `id` int NOT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `recipe_id` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `ingredients`
--

CREATE TABLE `ingredients` (
  `id` int NOT NULL,
  `recipe_id` int DEFAULT NULL,
  `ingredient` varchar(255) DEFAULT NULL,
  `quantity` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `recipes`
--

CREATE TABLE `recipes` (
  `id` int NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `steps`
--

CREATE TABLE `steps` (
  `id` int NOT NULL,
  `recipe_id` int DEFAULT NULL,
  `step_number` int DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `bagueton_recipe`
--
ALTER TABLE `bagueton_recipe`
  ADD PRIMARY KEY (`id_recipe`);

--
-- Index pour la table `images`
--
ALTER TABLE `images`
  ADD PRIMARY KEY (`id`),
  ADD KEY `recipe_id` (`recipe_id`);

--
-- Index pour la table `ingredients`
--
ALTER TABLE `ingredients`
  ADD PRIMARY KEY (`id`),
  ADD KEY `recipe_id` (`recipe_id`);

--
-- Index pour la table `recipes`
--
ALTER TABLE `recipes`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `steps`
--
ALTER TABLE `steps`
  ADD PRIMARY KEY (`id`),
  ADD KEY `recipe_id` (`recipe_id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `bagueton_recipe`
--
ALTER TABLE `bagueton_recipe`
  MODIFY `id_recipe` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT pour la table `images`
--
ALTER TABLE `images`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `ingredients`
--
ALTER TABLE `ingredients`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `recipes`
--
ALTER TABLE `recipes`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `steps`
--
ALTER TABLE `steps`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `images`
--
ALTER TABLE `images`
  ADD CONSTRAINT `images_ibfk_1` FOREIGN KEY (`recipe_id`) REFERENCES `recipes` (`id`);

--
-- Contraintes pour la table `ingredients`
--
ALTER TABLE `ingredients`
  ADD CONSTRAINT `ingredients_ibfk_1` FOREIGN KEY (`recipe_id`) REFERENCES `recipes` (`id`);

--
-- Contraintes pour la table `steps`
--
ALTER TABLE `steps`
  ADD CONSTRAINT `steps_ibfk_1` FOREIGN KEY (`recipe_id`) REFERENCES `recipes` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
