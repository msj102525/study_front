export const metadata = {
    title: "Sales job",
}

export default function AboutUsLayout({
    children,
}: {
    children: React.ReactNode
}) {
    return (
        <div>
            {children}
            &copy; Next JS is great!
        </div>
    )
}
