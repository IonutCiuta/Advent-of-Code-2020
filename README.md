# Advent of Code 2020

⭐️ All solutions implemented by me, without copy-paste from big ol' Google.

❄️ I tried to solve the challenges as they were launched. So far, I was only late with Day 11, part 2.

🎄 Notes on the puzzles:
* Day 10 was by far the most interesting till now.
The dummy recursive solution for part 2 clearly did not work as 1h30m later it still hadn't finished running.
Someone on Reddit mentioned something about memoization which reminded me of the Fibonacci recursive implementation.
With this implementation, the algorithm runs almost instantly.
* Day 11 has by far the messiest code till now. I've already implemented Conway's Game of Life a while ago so I wasn't very enthusiastic about doing it again.
* Day 15 was quite interesting. Definitely one fo the simplest requirements and implementations but the super large input
for part 2 made my initial solution worthless. Just as a reference, the implementation for part 1 processed about **1 mil.
turns** in **6 minutes**🤯 I assume the biggest overhead was due to the crude queue implementation. Final version for part 2 actually
finishes in **6 sec** 🙌🏻
* Day 16 was not that hard, but it was a lot of work. The main thing I was missing for part 2 was the understanding that a field can satisfy multiple rules, but there is only one field which satisfies exactly one rule. Then it was `easy`.
Also, multiply ints is a good indication that the result might be long(er)