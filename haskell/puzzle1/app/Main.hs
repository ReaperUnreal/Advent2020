module Main where

import Data.Maybe
import Text.Read

getLines :: IO [String]
getLines = lines <$> readFile "sources.txt"

parseInts :: [String] -> [Int]
parseInts ls = catMaybes $ readMaybe <$> ls

part1 :: [Int] -> Int
part1 xs = head [i * j | i <- xs, j <- xs, i /= j, i + j == 2020]

part2 :: [Int] -> Int
part2 xs = head [i * j * k | i <- xs, j <- xs, k <- xs, i /= j, j /= k, i /= k, i + j + k == 2020]

main :: IO ()
main = do
    ls <- getLines
    print $ part1 (parseInts ls)