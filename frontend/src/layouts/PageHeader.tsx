function PageHeader({ title, pageType }: { title: string, pageType: string }) {
    let fill;
    let className;
    let fillRule1: "evenodd" | "inherit" | "nonzero" | undefined;
    let imageUrl1;
    let fillRule2: "evenodd" | "inherit" | "nonzero" | undefined;
    let imageUrl2;
    switch (pageType) {
        case 'product':
            fill = 'currentColor';
            className = 'bi bi-box-seam-fill';
            fillRule1 = "evenodd";
            imageUrl1 = 'M15.528 2.973a.75.75 0 0 1 .472.696v8.662a.75.75 0 0 1-.472.696l-7.25 2.9a.75.75 0 0 1-.557 0l-7.25-2.9A.75.75 0 0 1 0 12.331V3.669a.75.75 0 0 1 .471-.696L7.443.184l.01-.003.268-.108a.75.75 0 0 1 .558 0l.269.108.01.003zM10.404 2 4.25 4.461 1.846 3.5 1 3.839v.4l6.5 2.6v7.922l.5.2.5-.2V6.84l6.5-2.6v-.4l-.846-.339L8 5.961 5.596 5l6.154-2.461z';
            fillRule2 = undefined;
            imageUrl2 = '';
            break;
        case 'category':
            fill = 'currentColor';
            className = 'bi bi-handbag';
            fillRule1 = undefined;
            imageUrl1 = 'M8 1a2 2 0 0 1 2 2v2H6V3a2 2 0 0 1 2-2m3 4V3a3 3 0 1 0-6 0v2H3.36a1.5 1.5 0 0 0-1.483 1.277L.85 13.13A2.5 2.5 0 0 0 3.322 16h9.355a2.5 2.5 0 0 0 2.473-2.87l-1.028-6.853A1.5 1.5 0 0 0 12.64 5zm-1 1v1.5a.5.5 0 0 0 1 0V6h1.639a.5.5 0 0 1 .494.426l1.028 6.851A1.5 1.5 0 0 1 12.678 15H3.322a1.5 1.5 0 0 1-1.483-1.723l1.028-6.851A.5.5 0 0 1 3.36 6H5v1.5a.5.5 0 1 0 1 0V6z';
            fillRule2 = undefined;
            imageUrl2 = '';
            break;
        default:
            fill = 'currentColor';
            className = 'bi bi-handbag';
            fillRule1 = undefined;
            imageUrl1 = 'M8 1a2 2 0 0 1 2 2v2H6V3a2 2 0 0 1 2-2m3 4V3a3 3 0 1 0-6 0v2H3.36a1.5 1.5 0 0 0-1.483 1.277L.85 13.13A2.5 2.5 0 0 0 3.322 16h9.355a2.5 2.5 0 0 0 2.473-2.87l-1.028-6.853A1.5 1.5 0 0 0 12.64 5zm-1 1v1.5a.5.5 0 0 0 1 0V6h1.639a.5.5 0 0 1 .494.426l1.028 6.851A1.5 1.5 0 0 1 12.678 15H3.322a1.5 1.5 0 0 1-1.483-1.723l1.028-6.851A.5.5 0 0 1 3.36 6H5v1.5a.5.5 0 1 0 1 0V6z';
            fillRule2 = undefined;
            imageUrl2 = '';
            break;
    }

    return (
        <div className="py-2 text-center">
            <svg xmlns="http://www.w3.org/2000/svg" width="72" height="72" fill={fill}
                 className={className} viewBox="0 0 16 16">
                <path fillRule={fillRule1} d={imageUrl1}/>
                <path fillRule={fillRule2} d={imageUrl2}/>
            </svg>
            <h2>{title}</h2>
        </div>
    );
}

export default PageHeader;