import Link from "next/link";
import { resolve } from "path";

export const metadata = {
    title: "Home",
}

export const API_URL = "https://nomad-movies.nomadcoders.workers.dev/movies";

async function getMovies() {
    // await new Promise((resolve) => setTimeout(resolve, 1100));
    const response = await fetch(API_URL);
    const json = await response.json();
    return json;
}

export default async function HomePage() {
    const movies = await getMovies();
    return (
        <div>
            {movies.map(movie => <li key={movie.id}>
                <Link href={`movies/${movie.id}`}>
                    {movie.title}
                </Link>
            </li>)}
        </div>
    )
}

// const ComponentNName = async() => {}

// "use client"

// import { useEffect, useState } from "react";

// export default function Page() {
//     const [isLoading, setIsLoading] = useState(true);
//     const [movies, setMovies] = useState([]);
//     const getMovies = async () => {
//         const response = await fetch("https://nomad-movies.nomadcoders.workers.dev/movies");
//         const json = await response.json();
//         setMovies(json);
//         setIsLoading(false);
//     }
//     useEffect(() => {
//         getMovies();
//     },[])
//     return (
//         <div>
//             {isLoading ? "Loading..." : JSON.stringify(movies)};
//         </div>
//     )
// }